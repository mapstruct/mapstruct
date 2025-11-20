/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor.creation;

import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.firstKey;
import static org.mapstruct.ap.internal.util.Collections.firstValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import org.mapstruct.ap.internal.conversion.ConversionProvider;
import org.mapstruct.ap.internal.conversion.Conversions;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext.MappingResolver;
import org.mapstruct.ap.internal.model.MethodReference;
import org.mapstruct.ap.internal.model.SupportingField;
import org.mapstruct.ap.internal.model.SupportingMappingMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.DefaultConversionContext;
import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMappingMethods;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionContext;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.MessageConstants;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * The one and only implementation of {@link MappingResolver}. The class has been split into an interface an
 * implementation for the sake of avoiding package dependencies. Specifically, this implementation refers to classes
 * which should not be exposed to the {@code model} package.
 *
 * @author Sjaak Derksen
 */
public class MappingResolverImpl implements MappingResolver {

    private static final int LIMIT_REPORTING_AMBIGUOUS = 5;

    private final FormattingMessager messager;
    private final TypeUtils typeUtils;
    private final TypeFactory typeFactory;

    private final List<Method> sourceModel;
    private final List<MapperReference> mapperReferences;

    private final Conversions conversions;
    private final BuiltInMappingMethods builtInMethods;
    private final MethodSelectors methodSelectors;

    private final boolean verboseLogging;

    private static final String JL_OBJECT_NAME = Object.class.getName();

    /**
     * Private methods which are not present in the original mapper interface and are added to map certain property
     * types.
     */
    private final Set<SupportingMappingMethod> usedSupportedMappings = new HashSet<>();

    /**
     * Private fields which are added to map certain property types.
     */
    private final Set<Field> usedSupportedFields = new HashSet<>();

    public MappingResolverImpl(FormattingMessager messager, ElementUtils elementUtils, TypeUtils typeUtils,
                               TypeFactory typeFactory, List<Method> sourceModel,
                               List<MapperReference> mapperReferences, boolean verboseLogging) {
        this.messager = messager;
        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;

        this.sourceModel = sourceModel;
        this.mapperReferences = mapperReferences;

        this.conversions = new Conversions( typeFactory );
        this.builtInMethods = new BuiltInMappingMethods( typeFactory );
        this.methodSelectors = new MethodSelectors( typeUtils, elementUtils, messager, null );

        this.verboseLogging = verboseLogging;
    }

    @Override
    public Assignment getTargetAssignment(Method mappingMethod, ForgedMethodHistory description, Type targetType,
                                          FormattingParameters formattingParameters,
                                          SelectionCriteria criteria, SourceRHS sourceRHS,
                                          AnnotationMirror positionHint,
                                          Supplier<Assignment> forger) {

        ResolvingAttempt attempt = new ResolvingAttempt(
            sourceModel,
            mappingMethod,
            description,
            formattingParameters,
            sourceRHS,
            criteria,
            positionHint,
            forger,
            builtInMethods.getBuiltInMethods(),
            messager,
            verboseLogging
        );

        return attempt.getTargetAssignment( sourceRHS.getSourceTypeForMatching(), targetType );
    }

    @Override
    public Set<SupportingMappingMethod> getUsedSupportedMappings() {
        return usedSupportedMappings;
    }

    @Override
    public Set<Field> getUsedSupportedFields() {
        return usedSupportedFields;
    }

    private MapperReference findMapperReference(Method method) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                ref.setUsed( ref.isUsed() || !method.isStatic() );
                ref.setTypeRequiresImport( true );
                return ref;
            }
        }
        return null;
    }

    private class ResolvingAttempt {

        private final Method mappingMethod;
        private final ForgedMethodHistory description;
        private final List<Method> methods;
        private final SelectionCriteria selectionCriteria;
        private final SourceRHS sourceRHS;
        private final FormattingParameters formattingParameters;
        private final AnnotationMirror positionHint;
        private final Supplier<Assignment> forger;
        private final List<BuiltInMethod> builtIns;
        private final FormattingMessager messager;
        private final int reportingLimitAmbiguous;

        // resolving via 2 steps creates the possibility of wrong matches, first builtin method matches,
        // second doesn't. In that case, the first builtin method should not lead to a supported method
        // so this set must be cleared.
        private final Set<SupportingMappingMethod> supportingMethodCandidates;

        // CHECKSTYLE:OFF
        private ResolvingAttempt(List<Method> sourceModel, Method mappingMethod, ForgedMethodHistory description,
                                 FormattingParameters formattingParameters, SourceRHS sourceRHS,
                                 SelectionCriteria criteria,
                                 AnnotationMirror positionHint,
                                 Supplier<Assignment> forger,
                                 List<BuiltInMethod> builtIns,
                                 FormattingMessager messager, boolean verboseLogging) {

            this.mappingMethod = mappingMethod;
            this.description = description;
            this.formattingParameters =
                formattingParameters == null ? FormattingParameters.EMPTY : formattingParameters;
            this.sourceRHS = sourceRHS;
            this.supportingMethodCandidates = new HashSet<>();
            this.selectionCriteria = criteria;
            this.positionHint = positionHint;
            this.forger = forger;
            this.builtIns = builtIns;
            this.messager = messager;
            this.reportingLimitAmbiguous = verboseLogging ? Integer.MAX_VALUE : LIMIT_REPORTING_AMBIGUOUS;
            this.methods = filterPossibleCandidateMethods( sourceModel, mappingMethod );
        }
        // CHECKSTYLE:ON

        private <T extends Method> List<T> filterPossibleCandidateMethods(List<T> candidateMethods, T mappingMethod) {
            List<T> result = new ArrayList<>( candidateMethods.size() );
            for ( T candidate : candidateMethods ) {
                if ( isCandidateForMapping( candidate ) && isNotSelfOrSelfAllowed( mappingMethod, candidate )) {
                    result.add( candidate );
                }
            }

            return result;
        }

        private <T extends Method> boolean isNotSelfOrSelfAllowed(T mappingMethod, T candidate) {
            return selectionCriteria == null || selectionCriteria.isSelfAllowed() || !candidate.equals( mappingMethod );
        }

        private Assignment getTargetAssignment(Type sourceType, Type targetType) {

            Assignment assignment;

            // first simple mapping method
            if ( allowMappingMethod() ) {
                List<SelectedMethod<Method>> matches = getBestMatch( methods, sourceType, targetType );
                reportErrorWhenAmbiguous( matches, targetType );
                if ( !matches.isEmpty() ) {
                    assignment = toMethodRef( first( matches ) );
                    assignment.setAssignment( sourceRHS );
                    return assignment;
                }
            }

            // then direct assignable
            if ( !hasQualfiers() ) {
                if ( ( sourceType.isAssignableTo( targetType ) ||
                    isAssignableThroughCollectionCopyConstructor( sourceType, targetType ) )
                    && allowDirect( sourceType, targetType ) ) {
                    Assignment simpleAssignment = sourceRHS;
                    return simpleAssignment;
                }
            }
            // At this point the SourceType will either
            // 1. be a String
            // 2. or when its a primitive / wrapped type and analysis successful equal to its TargetType. But in that
            //    case it should have been direct assignable.
            // In case of 1. and the target type is still a wrapped or primitive type we must assume that the check
            // in NativeType is not successful. We don't want to go through type conversion, double mappings etc.
            // with something that we already know to be wrong.
            if ( sourceType.isLiteral()
                && "java.lang.String".equals( sourceType.getFullyQualifiedName() )
                && targetType.isNative() ) {
                return null;
            }

            // then type conversion
            if ( allowConversion() ) {
                if ( !hasQualfiers() ) {
                    ConversionAssignment conversion = resolveViaConversion( sourceType, targetType );
                    if ( conversion != null ) {
                        conversion.reportMessageWhenNarrowing( messager, this );
                        conversion.getAssignment().setAssignment( sourceRHS );
                        return conversion.getAssignment();
                    }
                }

                // check for a built-in method
                if ( !hasQualfiers() ) {
                    List<SelectedMethod<BuiltInMethod>> matches = getBestMatch( builtIns, sourceType, targetType );
                    reportErrorWhenAmbiguous( matches, targetType );
                    if ( !matches.isEmpty() ) {
                        assignment = toBuildInRef( first( matches ) );
                        assignment.setAssignment( sourceRHS );
                        usedSupportedMappings.addAll( supportingMethodCandidates );
                        return assignment;
                    }
                }
            }

            if ( allow2Steps() ) {
                // 2 step method, first: method(method(source))
                assignment = MethodMethod.getBestMatch( this, sourceType, targetType );
                if ( assignment != null ) {
                    usedSupportedMappings.addAll( supportingMethodCandidates );
                    return assignment;
                }

                // 2 step method, then: method(conversion(source))
                if ( allowConversion() ) {
                    assignment = ConversionMethod.getBestMatch( this, sourceType, targetType );
                    if ( assignment != null ) {
                        usedSupportedMappings.addAll( supportingMethodCandidates );
                        return assignment;
                    }
                }

                // stop here when looking for update methods.
                selectionCriteria.setPreferUpdateMapping( false );

                // 2 step method, finally: conversion(method(source))
                if ( allowConversion() ) {
                    assignment = MethodConversion.getBestMatch( this, sourceType, targetType );
                    if ( assignment != null ) {
                        usedSupportedMappings.addAll( supportingMethodCandidates );
                        return assignment;
                    }
                }
            }

            if ( hasQualfiers() ) {
                if ((sourceType.isCollectionType() || sourceType.isArrayType()) && targetType.isIterableType()) {
                    // Allow forging iterable mapping when no iterable mapping already found
                    return forger.get();
                }
                else {
                    printQualifierMessage( selectionCriteria );
                }
            }
            else if ( allowMappingMethod() ) {
                // only forge if we would allow mapping method
                return forger.get();
            }

            // if nothing works, alas, the result is null
            return null;
        }

        private boolean hasQualfiers() {
            return selectionCriteria != null && selectionCriteria.hasQualfiers();
        }

        private void printQualifierMessage(SelectionCriteria selectionCriteria ) {

            List<String> annotations = selectionCriteria.getQualifiers().stream()
                .filter( DeclaredType.class::isInstance )
                .map( DeclaredType.class::cast )
                .map( DeclaredType::asElement )
                .map( Element::getSimpleName )
                .map( Name::toString )
                .map( a -> "@" + a )
                .collect( Collectors.toList() );
            List<String> names = selectionCriteria.getQualifiedByNames();

            if ( !annotations.isEmpty() && !names.isEmpty() ) {
                messager.printMessage(
                    mappingMethod.getExecutable(),
                    positionHint,
                    Message.GENERAL_NO_QUALIFYING_METHOD_COMBINED,
                    Strings.join( names, MessageConstants.AND ),
                    Strings.join( annotations, MessageConstants.AND )
                );
            }
            else if ( !annotations.isEmpty() ) {
                messager.printMessage(
                    mappingMethod.getExecutable(),
                    positionHint,
                    Message.GENERAL_NO_QUALIFYING_METHOD_ANNOTATION,
                    Strings.join( annotations, MessageConstants.AND )
                );
            }
            else  {
                messager.printMessage(
                    mappingMethod.getExecutable(),
                    positionHint,
                    Message.GENERAL_NO_QUALIFYING_METHOD_NAMED,
                    Strings.join( names, MessageConstants.AND )
                );
            }

        }

        private boolean allowDirect( Type sourceType, Type targetType ) {
            if ( selectionCriteria != null && selectionCriteria.isAllowDirect() ) {
                return true;
            }

            return allowDirect( sourceType ) || allowDirect( targetType );
        }

        private boolean allowDirect(Type type) {
            if ( type.isPrimitive() ) {
                return true;
            }

            if ( type.isEnumType() ) {
                return true;
            }

            if ( type.isArrayType() ) {
                return type.isJavaLangType() || type.getComponentType().isPrimitive();
            }

            if ( type.isIterableOrStreamType() ) {
                List<Type> typeParameters = type.getTypeParameters();
                // For iterable or stream direct mapping is enabled when:
                // - The type is raw (no type parameters)
                // - The type parameter is allowed
                return typeParameters.isEmpty() || allowDirect( Collections.first( typeParameters ) );
            }

            if ( type.isMapType() ) {
                List<Type> typeParameters = type.getTypeParameters();
                // For map type direct mapping is enabled when:
                // - The type os raw (no type parameters
                // - The key and value are direct assignable
                return typeParameters.isEmpty() ||
                    ( allowDirect( typeParameters.get( 0 ) ) && allowDirect( typeParameters.get( 1 ) ) );
            }

            return type.isJavaLangType();
        }

        private boolean allowConversion() {
            return selectionCriteria != null && selectionCriteria.isAllowConversion();
        }

        private boolean allowMappingMethod() {
            return selectionCriteria != null && selectionCriteria.isAllowMappingMethod();
        }

        private boolean allow2Steps() {
            return selectionCriteria != null && selectionCriteria.isAllow2Steps();
        }

        private ConversionAssignment resolveViaConversion(Type sourceType, Type targetType) {

            ConversionProvider conversionProvider = conversions.getConversion( sourceType, targetType );
            if ( conversionProvider == null ) {
                return null;
            }
            ConversionContext ctx = new DefaultConversionContext(
                typeFactory,
                messager,
                sourceType,
                targetType,
                formattingParameters
            );

            // add helper methods required in conversion
            Set<Field> allUsedFields = new HashSet<>( mapperReferences );
            SupportingField.addAllFieldsIn( supportingMethodCandidates, allUsedFields );

            for ( FieldReference helperField : conversionProvider.getRequiredHelperFields( ctx )) {
                Field field = SupportingField.getSafeField( null, helperField, allUsedFields );
                allUsedFields.add( field );
                usedSupportedFields.add( field );
            }

            for ( HelperMethod helperMethod : conversionProvider.getRequiredHelperMethods( ctx ) ) {
                SupportingMappingMethod supportingMappingMethod =
                    new SupportingMappingMethod( helperMethod );
                SupportingField.addAllFieldsIn( Collections.asSet( supportingMappingMethod ), allUsedFields );
                usedSupportedMappings.add( supportingMappingMethod );
            }

            Assignment conversion = conversionProvider.to( ctx );
            if ( conversion != null ) {
                return new ConversionAssignment( sourceType, targetType, conversionProvider.to( ctx ) );
            }
            return null;
        }

        private boolean isCandidateForMapping(Method methodCandidate) {
            if ( methodCandidate.getConditionOptions().isAnyStrategyApplicable() ) {
                return false;
            }
            return isCreateMethodForMapping( methodCandidate ) || isUpdateMethodForMapping( methodCandidate );
        }

        private boolean isCreateMethodForMapping(Method methodCandidate) {
            // a create method may not return void and has no target parameter
            return methodCandidate.getSourceParameters().size() == 1
                && !methodCandidate.getReturnType().isVoid()
                && methodCandidate.getMappingTargetParameter() == null
                && !methodCandidate.isLifecycleCallbackMethod();
        }

        private boolean isUpdateMethodForMapping(Method methodCandidate) {
            // an update method may, or may not return void and has a target parameter
            return methodCandidate.getSourceParameters().size() == 1
                && methodCandidate.getMappingTargetParameter() != null
                && !methodCandidate.isLifecycleCallbackMethod();
        }

        private <T extends Method> List<SelectedMethod<T>> getBestMatch(List<T> methods, Type source, Type target) {
            return methodSelectors.getMatchingMethods(
                methods,
                SelectionContext.forMappingMethods( mappingMethod, source, target, selectionCriteria, typeFactory )
            );
        }

        private <T extends Method> void reportErrorWhenAmbiguous(List<SelectedMethod<T>> candidates, Type target) {

            // raise an error if more than one mapping method is suitable to map the given source type
            // into the target type
            if ( candidates.size() > 1 ) {

                String descriptionStr = "";
                if ( description != null ) {
                    descriptionStr = description.createSourcePropertyErrorMessage();
                }
                else {
                    descriptionStr = sourceRHS.getSourceErrorMessagePart();
                }

                if ( sourceRHS.getSourceErrorMessagePart() != null ) {
                    messager.printMessage(
                        mappingMethod.getExecutable(),
                        positionHint,
                        Message.GENERAL_AMBIGUOUS_MAPPING_METHOD,
                        descriptionStr,
                        target.describe(),
                        join( candidates )
                    );
                }
                else {
                    messager.printMessage(
                        mappingMethod.getExecutable(),
                        positionHint,
                        Message.GENERAL_AMBIGUOUS_FACTORY_METHOD,
                        target.describe(),
                        join( candidates )
                    );
                }
            }
        }

        private Assignment toMethodRef(SelectedMethod<Method> selectedMethod) {
            MapperReference mapperReference = findMapperReference( selectedMethod.getMethod() );

            return MethodReference.forMapperReference(
                selectedMethod.getMethod(),
                mapperReference,
                selectedMethod.getParameterBindings()
            );
        }

        private Assignment toBuildInRef(SelectedMethod<BuiltInMethod> selectedMethod) {
            BuiltInMethod method = selectedMethod.getMethod();
            Set<Field> allUsedFields = new HashSet<>( mapperReferences );
            SupportingField.addAllFieldsIn( supportingMethodCandidates, allUsedFields );
            SupportingMappingMethod supportingMappingMethod = new SupportingMappingMethod( method, allUsedFields );
            supportingMethodCandidates.add( supportingMappingMethod );
            ConversionContext ctx = new DefaultConversionContext(
                typeFactory,
                messager,
                method.getMappingSourceType(),
                method.getResultType(),
                formattingParameters
            );
            Assignment methodReference = MethodReference.forBuiltInMethod( method, ctx );
            methodReference.setAssignment( sourceRHS );
            return methodReference;
        }

        /**
         * Whether the given source and target type are both a collection type or both a map type and the source value
         * can be propagated via a copy constructor.
         */
        private boolean isAssignableThroughCollectionCopyConstructor(Type sourceType, Type targetType) {
            boolean bothCollectionOrMap = false;

            if ( ( sourceType.isCollectionType() && targetType.isCollectionType() ) ||
                ( sourceType.isMapType() && targetType.isMapType() ) ) {
                bothCollectionOrMap = true;
            }

            if ( bothCollectionOrMap ) {
                return hasCompatibleCopyConstructor(
                    sourceType,
                    targetType.getImplementationType() != null ? targetType.getImplementationType() : targetType
                );
            }

            return false;
        }

        /**
         * Whether the given target type has a single-argument constructor which accepts the given source type.
         *
         * @param sourceType the source type
         * @param targetType the target type
         *
         * @return {@code true} if the target type has a constructor accepting the given source type, {@code false}
         * otherwise.
         */
        private boolean hasCompatibleCopyConstructor(Type sourceType, Type targetType) {
            if ( targetType.isPrimitive() ) {
                return false;
            }

            List<ExecutableElement> targetTypeConstructors = ElementFilter.constructorsIn(
                targetType.getTypeElement().getEnclosedElements() );

            for ( ExecutableElement constructor : targetTypeConstructors ) {
                if ( constructor.getParameters().size() != 1 ) {
                    continue;
                }

                // get the constructor resolved against the type arguments of specific target type
                ExecutableType typedConstructor = (ExecutableType) typeUtils.asMemberOf(
                    (DeclaredType) targetType.getTypeMirror(),
                    constructor
                );

                TypeMirror parameterType = Collections.first( typedConstructor.getParameterTypes() );
                if ( parameterType.getKind() == TypeKind.DECLARED ) {
                    // replace any possible type bounds in the type parameters of the parameter types, as in JDK super
                    // type bounds in the arguments are returned from asMemberOf with "? extends ? super XX"
                    //
                    // It might also be enough to just remove "? super" from type parameters of
                    // targetType.getTypeMirror() in case we're in JDK. And that would be something that should be
                    // handled in SpecificCompilerWorkarounds...

                    DeclaredType p = (DeclaredType) parameterType;
                    List<TypeMirror> typeArguments = new ArrayList<>( p.getTypeArguments().size() );

                    for ( TypeMirror tArg : p.getTypeArguments() ) {
                        typeArguments.add( typeFactory.getTypeBound( tArg ) );
                    }
                    parameterType = typeUtils.getDeclaredType(
                        (TypeElement) p.asElement(),
                        typeArguments.toArray( new TypeMirror[typeArguments.size()] )
                    );
                }

                if ( typeUtils.isAssignable( sourceType.getTypeMirror(), parameterType ) ) {
                    return true;
                }
            }

            return false;
        }

        private <T extends Method> String join( List<SelectedMethod<T>> candidates ) {

            String candidateStr = candidates.stream()
                                            .limit( reportingLimitAmbiguous )
                                            .map( m -> m.getMethod().describe() )
                                            .collect( Collectors.joining( ", " ) );

            if ( candidates.size() > reportingLimitAmbiguous ) {
                candidateStr += String.format( "... and %s more", candidates.size() - reportingLimitAmbiguous );
            }
            return candidateStr;
        }
    }

    private static class ConversionAssignment {

        private final Type sourceType;
        private final Type targetType;
        private final Assignment assignment;

        ConversionAssignment(Type sourceType, Type targetType, Assignment assignment) {
            this.sourceType = sourceType;
            this.targetType = targetType;
            this.assignment = assignment;
        }

        Assignment getAssignment() {
            return assignment;
        }

        void reportMessageWhenNarrowing(FormattingMessager messager, ResolvingAttempt attempt) {

            if ( NativeTypes.isNarrowing( sourceType.getFullyQualifiedName(), targetType.getFullyQualifiedName() ) ) {
                ReportingPolicyGem policy = attempt.mappingMethod.getOptions().getMapper().typeConversionPolicy();
                if ( policy == ReportingPolicyGem.WARN ) {
                    report( messager, attempt, Message.CONVERSION_LOSSY_WARNING );
                }
                else if ( policy == ReportingPolicyGem.ERROR ) {
                    report( messager, attempt, Message.CONVERSION_LOSSY_ERROR );
                }
            }
        }

        private void report(FormattingMessager messager, ResolvingAttempt attempt, Message message) {

            messager.printMessage(
                attempt.mappingMethod.getExecutable(),
                attempt.positionHint,
                message,
                attempt.sourceRHS.getSourceErrorMessagePart(),
                sourceType.describe(),
                targetType.describe()
            );
        }

        String shortName() {
            return sourceType.getName() + "-->" + targetType.getName();
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }
            ConversionAssignment that = (ConversionAssignment) o;
            return sourceType.equals( that.sourceType ) && targetType.equals( that.targetType );
        }

        @Override
        public int hashCode() {
            return Objects.hash( sourceType, targetType );
        }
    }

    private enum BestMatchType {
        IGNORE_QUALIFIERS_BEFORE_Y_CANDIDATES,
        IGNORE_QUALIFIERS_AFTER_Y_CANDIDATES,
    }

    /**
     * Suppose mapping required from A to C and:
     * <ul>
     * <li>no direct referenced mapping method either built-in or referenced is available from A to C</li>
     * <li>no conversion is available</li>
     * <li>there is a method from A to B, methodX</li>
     * <li>there is a method from B to C, methodY</li>
     * </ul>
     * then this method tries to resolve this combination and make a mapping methodY( methodX ( parameter ) )
     *
     * NOTE method X cannot be an update method
     */
    private static class MethodMethod<T1 extends Method, T2 extends Method> {

        private final ResolvingAttempt attempt;
        private final List<T1> xMethods;
        private final List<T2> yMethods;
        private final Function<SelectedMethod<T1>, Assignment> xCreate;
        private final Function<SelectedMethod<T2>, Assignment> yCreate;

        // results
        private boolean hasResult = false;
        private Assignment result = null;

        static Assignment getBestMatch(ResolvingAttempt att, Type sourceType, Type targetType) {
            MethodMethod<Method, Method> mmAttempt =
                new MethodMethod<>( att, att.methods, att.methods, att::toMethodRef, att::toMethodRef )
                    .getBestMatch( sourceType, targetType );
            if ( mmAttempt.hasResult ) {
                return mmAttempt.result;
            }
            if ( att.hasQualfiers() ) {
                mmAttempt = mmAttempt.getBestMatchIgnoringQualifiersBeforeY( sourceType, targetType );
                if ( mmAttempt.hasResult ) {
                    return mmAttempt.result;
                }

                mmAttempt = mmAttempt.getBestMatchIgnoringQualifiersAfterY( sourceType, targetType );
                if ( mmAttempt.hasResult ) {
                    return mmAttempt.result;
                }
            }
            if ( att.allowConversion() ) {
                MethodMethod<Method, BuiltInMethod> mbAttempt =
                    new MethodMethod<>( att, att.methods, att.builtIns, att::toMethodRef, att::toBuildInRef )
                        .getBestMatch( sourceType, targetType );
                if ( mbAttempt.hasResult ) {
                    return mbAttempt.result;
                }
                MethodMethod<BuiltInMethod, Method> bmAttempt =
                    new MethodMethod<>( att, att.builtIns, att.methods, att::toBuildInRef, att::toMethodRef )
                        .getBestMatch( sourceType, targetType );
                if ( bmAttempt.hasResult ) {
                    return bmAttempt.result;
                }
                MethodMethod<BuiltInMethod, BuiltInMethod> bbAttempt =
                    new MethodMethod<>( att, att.builtIns, att.builtIns, att::toBuildInRef, att::toBuildInRef )
                        .getBestMatch( sourceType, targetType );
                return bbAttempt.result;
            }

            return null;
        }

        MethodMethod(ResolvingAttempt attempt, List<T1> xMethods, List<T2> yMethods,
                            Function<SelectedMethod<T1>, Assignment> xCreate,
                            Function<SelectedMethod<T2>, Assignment> yCreate) {
            this.attempt = attempt;
            this.xMethods = xMethods;
            this.yMethods = yMethods;
            this.xCreate = xCreate;
            this.yCreate = yCreate;
        }

        private MethodMethod<T1, T2> getBestMatch(Type sourceType, Type targetType) {
            return getBestMatch( sourceType, targetType, null );
        }

        private MethodMethod<T1, T2> getBestMatchIgnoringQualifiersBeforeY(Type sourceType, Type targetType) {
            return getBestMatch( sourceType, targetType, BestMatchType.IGNORE_QUALIFIERS_BEFORE_Y_CANDIDATES );
        }

        private MethodMethod<T1, T2> getBestMatchIgnoringQualifiersAfterY(Type sourceType, Type targetType) {
            return getBestMatch( sourceType, targetType, BestMatchType.IGNORE_QUALIFIERS_AFTER_Y_CANDIDATES );
        }

        private MethodMethod<T1, T2> getBestMatch(Type sourceType, Type targetType, BestMatchType matchType) {

            Set<T2> yCandidates = new HashSet<>();
            Map<SelectedMethod<T1>, List<SelectedMethod<T2>>> xCandidates = new LinkedHashMap<>();
            Map<SelectedMethod<T1>, Type> typesInTheMiddle = new LinkedHashMap<>();

            // Iterate over all source methods. Check if the return type matches with the parameter that we need.
            // so assume we need a method from A to C we look for a methodX from A to B (all methods in the
            // list form such a candidate).
            // For each of the candidates, we need to look if there's a methodY, either
            // sourceMethod or builtIn that fits the signature B to C. Only then there is a match. If we have a match
            // a nested method call can be called. so C = methodY( methodX (A) )
            attempt.selectionCriteria.setPreferUpdateMapping( false );
            attempt.selectionCriteria.setIgnoreQualifiers(
                matchType == BestMatchType.IGNORE_QUALIFIERS_BEFORE_Y_CANDIDATES );

            for ( T2 yCandidate : yMethods ) {
                Type ySourceType = yCandidate.getMappingSourceType();
                ySourceType = ySourceType.resolveGenericTypeParameters( targetType, yCandidate.getResultType() );
                Type yTargetType = yCandidate.getResultType();
                if ( ySourceType == null
                    || !yTargetType.isRawAssignableTo( targetType )
                    || JL_OBJECT_NAME.equals( ySourceType.getFullyQualifiedName() ) ) {
                    //  java.lang.Object as intermediate result
                    continue;
                }
                List<SelectedMethod<T1>> xMatches = attempt.getBestMatch( xMethods, sourceType, ySourceType );
                if ( !xMatches.isEmpty() ) {
                    for ( SelectedMethod<T1> x : xMatches ) {
                        xCandidates.put( x, new ArrayList<>() );
                        typesInTheMiddle.put( x, ySourceType );
                    }
                    yCandidates.add( yCandidate );
                }
            }
            attempt.selectionCriteria.setPreferUpdateMapping( true );
            attempt.selectionCriteria.setIgnoreQualifiers(
                matchType == BestMatchType.IGNORE_QUALIFIERS_AFTER_Y_CANDIDATES );

            // collect all results
            List<T2> yCandidatesList = new ArrayList<>( yCandidates );
            Iterator<Map.Entry<SelectedMethod<T1>, List<SelectedMethod<T2>>>> i = xCandidates.entrySet().iterator();
            while ( i.hasNext() ) {
                Map.Entry<SelectedMethod<T1>, List<SelectedMethod<T2>>> entry = i.next();
                Type typeInTheMiddle = typesInTheMiddle.get( entry.getKey() );
                entry.getValue().addAll( attempt.getBestMatch( yCandidatesList, typeInTheMiddle, targetType ) );
                if ( entry.getValue().isEmpty() ) {
                    i.remove();
                }
            }

            attempt.selectionCriteria.setIgnoreQualifiers( false );
            // no results left
            if ( xCandidates.isEmpty() ) {
                return this;
            }
            hasResult = true;

            // get result, there should be one entry left with only one value
            if ( xCandidates.size() == 1 && firstValue( xCandidates ).size() == 1 ) {
                Assignment methodRefY = yCreate.apply( first( firstValue( xCandidates ) ) );
                Assignment methodRefX = xCreate.apply( firstKey( xCandidates ) );
                methodRefY.setAssignment( methodRefX );
                methodRefX.setAssignment( attempt.sourceRHS );
                result = methodRefY;
            }
            else  {
                reportAmbiguousError( xCandidates, targetType );
            }
            return this;

        }

        void reportAmbiguousError(Map<SelectedMethod<T1>, List<SelectedMethod<T2>>> xCandidates, Type target) {
            StringBuilder result = new StringBuilder();
            xCandidates.entrySet()
                       .stream()
                       .limit( attempt.reportingLimitAmbiguous )
                       .forEach( e -> result.append( "method(s)Y: " )
                                            .append( attempt.join( e.getValue() ) )
                                            .append( ", methodX: " )
                                            .append( e.getKey().getMethod().describe() )
                                            .append( "; " ) );
            attempt.messager.printMessage(
                attempt.mappingMethod.getExecutable(),
                attempt.positionHint,
                Message.GENERAL_AMBIGUOUS_MAPPING_METHODY_METHODX,
                attempt.sourceRHS.getSourceType().getName() + " " + attempt.sourceRHS.getSourceParameterName(),
                target.getName(),
                result.toString() );
        }
    }

    /**
     * Suppose mapping required from A to C and:
     * <ul>
     * <li>there is a conversion from A to B, conversionX</li>
     * <li>there is a method from B to C, methodY</li>
     * </ul>
     * then this method tries to resolve this combination and make a mapping methodY( conversionX ( parameter ) )
     *
     * Instead of directly using a built in method candidate, all the return types as 'B' of all available built-in
     * methods are used to resolve a mapping (assignment) from result type to 'B'. If  a match is found, an attempt
     * is done to find a matching type conversion.
     */
    private static class ConversionMethod<T extends Method> {

        private final ResolvingAttempt attempt;
        private final List<T> methods;
        private final Function<SelectedMethod<T>, Assignment> create;

        // results
        private boolean hasResult = false;
        private Assignment result = null;

        static Assignment getBestMatch(ResolvingAttempt att, Type sourceType, Type targetType) {
            ConversionMethod<Method> mAttempt = new ConversionMethod<>( att, att.methods, att::toMethodRef )
                    .getBestMatch( sourceType, targetType );
            if ( mAttempt.hasResult ) {
                return mAttempt.result;
            }
            ConversionMethod<BuiltInMethod> bAttempt =
                new ConversionMethod<>( att, att.builtIns, att::toBuildInRef )
                    .getBestMatch( sourceType, targetType );
            return bAttempt.result;
        }

        ConversionMethod(ResolvingAttempt attempt, List<T> methods, Function<SelectedMethod<T>, Assignment> create) {
            this.attempt = attempt;
            this.methods = methods;
            this.create = create;
        }

        private ConversionMethod<T> getBestMatch(Type sourceType, Type targetType) {

            List<T> yCandidates = new ArrayList<>();
            Map<ConversionAssignment, List<SelectedMethod<T>>> xRefCandidates = new LinkedHashMap<>();

            for ( T yCandidate : methods ) {
                Type ySourceType = yCandidate.getMappingSourceType();
                ySourceType = ySourceType.resolveParameterToType( targetType, yCandidate.getResultType() ).getMatch();
                Type yTargetType = yCandidate.getResultType();
                if ( ySourceType == null
                    || !yTargetType.isRawAssignableTo( targetType )
                    || JL_OBJECT_NAME.equals( ySourceType.getFullyQualifiedName() ) ) {
                    //  java.lang.Object as intermediate result
                    continue;
                }
                ConversionAssignment xRefCandidate = attempt.resolveViaConversion( sourceType, ySourceType );
                if ( xRefCandidate != null ) {
                    xRefCandidates.put( xRefCandidate, new ArrayList<>() );
                    yCandidates.add( yCandidate );
                }
            }

            // collect all results
            Iterator<Map.Entry<ConversionAssignment, List<SelectedMethod<T>>>> i = xRefCandidates.entrySet().iterator();
            while ( i.hasNext() ) {
                Map.Entry<ConversionAssignment, List<SelectedMethod<T>>> entry = i.next();
                entry.getValue().addAll( attempt.getBestMatch( yCandidates, entry.getKey().targetType, targetType ) );
                if ( entry.getValue().isEmpty() ) {
                    i.remove();
                }
            }

            // no results left
            if ( xRefCandidates.isEmpty() ) {
                return this;
            }
            hasResult = true;

            // get result, there should be one entry left with only one value
            if ( xRefCandidates.size() == 1 && firstValue( xRefCandidates ).size() == 1 ) {
                Assignment methodRefY = create.apply( first( firstValue( xRefCandidates ) ) );
                ConversionAssignment conversionRefX = firstKey( xRefCandidates );
                conversionRefX.reportMessageWhenNarrowing( attempt.messager, attempt );
                methodRefY.setAssignment( conversionRefX.assignment );
                conversionRefX.assignment.setAssignment( attempt.sourceRHS );
                result = methodRefY;
            }
            else  {
                reportAmbiguousError( xRefCandidates, targetType );
            }
            return this;

        }

        void reportAmbiguousError(Map<ConversionAssignment, List<SelectedMethod<T>>> xRefCandidates, Type target) {
            StringBuilder result = new StringBuilder();
            xRefCandidates.entrySet()
                          .stream()
                          .limit( attempt.reportingLimitAmbiguous )
                          .forEach( e -> result.append( "method(s)Y: " )
                                               .append( attempt.join( e.getValue() ) )
                                               .append( ", conversionX: " )
                                               .append( e.getKey().shortName() )
                                               .append( "; " ) );
            attempt.messager.printMessage(
                attempt.mappingMethod.getExecutable(),
                attempt.positionHint,
                Message.GENERAL_AMBIGUOUS_MAPPING_METHODY_CONVERSIONX,
                attempt.sourceRHS.getSourceType().getName() + " " + attempt.sourceRHS.getSourceParameterName(),
                target.getName(),
                result.toString() );
        }
    }

    /**
     * Suppose mapping required from A to C and:
     * <ul>
     * <li>there is a method from A to B, methodX</li>
     * <li>there is a conversion from B to C, conversionY</li>
     * </ul>
     * then this method tries to resolve this combination and make a mapping conversionY( methodX ( parameter ) )
     *
     * Instead of directly using a built in method candidate, all the return types as 'B' of all available built-in
     * methods are used to resolve a mapping (assignment) from source type to 'B'. If a match is found, an attempt
     * is done to find a matching type conversion.
     *
     * NOTE methodX cannot be an update method
     */
    private static class MethodConversion<T extends Method> {

        private final ResolvingAttempt attempt;
        private final List<T> methods;
        private final Function<SelectedMethod<T>, Assignment> create;

        // results
        private boolean hasResult = false;
        private Assignment result = null;

        static Assignment getBestMatch(ResolvingAttempt att, Type sourceType, Type targetType) {
            MethodConversion<Method> mAttempt = new MethodConversion<>( att, att.methods, att::toMethodRef )
                    .getBestMatch( sourceType, targetType );
            if ( mAttempt.hasResult ) {
                return mAttempt.result;
            }
            MethodConversion<BuiltInMethod> bAttempt = new MethodConversion<>( att, att.builtIns, att::toBuildInRef )
                    .getBestMatch( sourceType, targetType );
            return bAttempt.result;
        }

        MethodConversion(ResolvingAttempt attempt, List<T> methods, Function<SelectedMethod<T>, Assignment> create) {
            this.attempt = attempt;
            this.methods = methods;
            this.create = create;
        }

        private MethodConversion<T> getBestMatch(Type sourceType, Type targetType) {

            List<T> xCandidates = new ArrayList<>();
            Map<ConversionAssignment, List<SelectedMethod<T>>> yRefCandidates = new LinkedHashMap<>();

            // search through methods, and select egible candidates
            for ( T xCandidate : methods ) {
                Type xTargetType = xCandidate.getReturnType();
                Type xSourceType = xCandidate.getMappingSourceType();
                xTargetType = xTargetType.resolveParameterToType( sourceType, xSourceType ).getMatch();
                if ( xTargetType == null
                    || xCandidate.isUpdateMethod()
                    || !sourceType.isRawAssignableTo( xSourceType )
                    || JL_OBJECT_NAME.equals( xTargetType.getFullyQualifiedName() ) ) {
                    // skip update methods || java.lang.Object as intermediate result
                    continue;
                }
                ConversionAssignment yRefCandidate = attempt.resolveViaConversion( xTargetType, targetType );
                if ( yRefCandidate != null ) {
                    yRefCandidates.put( yRefCandidate, new ArrayList<>() );
                    xCandidates.add( xCandidate );
                }
            }

            // collect all results
            Iterator<Map.Entry<ConversionAssignment, List<SelectedMethod<T>>>> i = yRefCandidates.entrySet().iterator();
            while ( i.hasNext() ) {
                Map.Entry<ConversionAssignment, List<SelectedMethod<T>>> entry = i.next();
                entry.getValue().addAll( attempt.getBestMatch( xCandidates, sourceType, entry.getKey().sourceType ) );
                if ( entry.getValue().isEmpty() ) {
                    i.remove();
                }
            }

            // no results left
            if ( yRefCandidates.isEmpty() ) {
                return this;
            }
            hasResult = true;

            // get result, there should be one entry left with only one value
            if ( yRefCandidates.size() == 1 && firstValue( yRefCandidates ).size() == 1 ) {
                Assignment methodRefX = create.apply( first( firstValue( yRefCandidates ) ) );
                ConversionAssignment conversionRefY = firstKey( yRefCandidates );
                conversionRefY.reportMessageWhenNarrowing( attempt.messager, attempt );
                methodRefX.setAssignment( attempt.sourceRHS );
                conversionRefY.assignment.setAssignment( methodRefX );
                result = conversionRefY.assignment;
            }
            else  {
                reportAmbiguousError( yRefCandidates, targetType );
            }
            return this;

        }

        void reportAmbiguousError(Map<ConversionAssignment, List<SelectedMethod<T>>> yRefCandidates, Type target) {
            StringBuilder result = new StringBuilder();
            yRefCandidates.entrySet()
                          .stream()
                          .limit( attempt.reportingLimitAmbiguous )
                          .forEach( e -> result.append( "conversionY: " )
                                               .append( e.getKey().shortName() )
                                               .append( ", method(s)X: " )
                                               .append( attempt.join( e.getValue() ) )
                                               .append( "; " ) );
            attempt.messager.printMessage(
                attempt.mappingMethod.getExecutable(),
                attempt.positionHint,
                Message.GENERAL_AMBIGUOUS_MAPPING_CONVERSIONY_METHODX,
                attempt.sourceRHS.getSourceType().getName() + " " + attempt.sourceRHS.getSourceParameterName(),
                target.getName(),
                result.toString() );
        }
    }

}
