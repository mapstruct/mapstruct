/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Services;
import org.mapstruct.ap.spi.MappingExclusionProvider;

/**
 * This class provides the context for the builders.
 * <p>
 * The context provides:
 * <ul>
 * <li>Input for the building process, such as the source model (mapping methods found) and mapper references.</li>
 * <li>Required factory, utility, reporting methods for building the mappings.</li>
 * <li>Means to harbor results produced by the builders, such as forged- and supported mapping methods that should be
 * generated in a later stage.</li>
 * </ul>
 *
 * @author Sjaak Derksen
 */
public class MappingBuilderContext {

    private static final MappingExclusionProvider SUB_MAPPING_EXCLUSION_PROVIDER = Services.get(
        MappingExclusionProvider.class,
        new DefaultMappingExclusionProvider()
    );

    /**
     * Resolves the most suitable way for mapping an element (property, iterable element etc.) from source to target.
     * There are 2 basic types of mappings:
     * <ul>
     * <li>conversions</li>
     * <li>methods</li>
     * </ul>
     * conversions are essentially one line mappings, such as String to Integer and Integer to Long methods come in some
     * varieties:
     * <ul>
     * <li>referenced mapping methods, these are methods implemented (or referenced) by the user. Sometimes indicated
     * with the 'uses' in the mapping annotations or part of the abstract mapper class</li>
     * <li>generated mapping methods (by means of MapStruct)</li>
     * <li>built in methods</li>
     * </ul>
     *
     * @author Sjaak Derksen
     */
    public interface MappingResolver {

        /**
         * returns a parameter assignment
         *
         * @param mappingMethod target mapping method
         * @param targetType return type to match
         * @param formattingParameters used for formatting dates and numbers
         * @param criteria parameters criteria in the selection process
         * @param sourceRHS source information
         * @param positionHint the mirror for reporting problems
         *
         * @return an assignment to a method parameter, which can either be:
         * <ol>
         * <li>MethodReference</li>
         * <li>TypeConversion</li>
         * <li>SourceRHS Assignment (empty TargetAssignment)</li>
         * <li>null, no assignment found</li>
         * </ol>
         */
        Assignment getTargetAssignment(Method mappingMethod, Type targetType,
                                       FormattingParameters formattingParameters,
                                       SelectionCriteria criteria, SourceRHS sourceRHS,
                                       AnnotationMirror positionHint,
                                       Supplier<Assignment> forger);

        Set<SupportingMappingMethod> getUsedSupportedMappings();
    }

    private final TypeFactory typeFactory;
    private final Elements elementUtils;
    private final Types typeUtils;
    private final FormattingMessager messager;
    private final AccessorNamingUtils accessorNaming;
    private final Options options;
    private final TypeElement mapperTypeElement;
    private final List<SourceMethod> sourceModel;
    private final List<MapperReference> mapperReferences;
    private final MappingResolver mappingResolver;
    private final List<MappingMethod> mappingsToGenerate = new ArrayList<>();
    private final Map<ForgedMethod, ForgedMethod> forgedMethodsUnderCreation =
        new HashMap<>();

    public MappingBuilderContext(TypeFactory typeFactory,
                          Elements elementUtils,
                          Types typeUtils,
                          FormattingMessager messager,
                          AccessorNamingUtils accessorNaming,
                          Options options,
                          MappingResolver mappingResolver,
                          TypeElement mapper,
                          List<SourceMethod> sourceModel,
                          List<MapperReference> mapperReferences) {
        this.typeFactory = typeFactory;
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        this.accessorNaming = accessorNaming;
        this.options = options;
        this.mappingResolver = mappingResolver;
        this.mapperTypeElement = mapper;
        this.sourceModel = sourceModel;
        this.mapperReferences = mapperReferences;
    }

    /**
     * Returns a map which is used to track which forged methods are under creation.
     * Used for cutting the possible infinite recursion of forged method creation.
     *
     * Map is used instead of set because not all fields of ForgedMethods are used in equals/hashCode and we are
     * interested only in the first created ForgedMethod
     *
     * @return map of forged methods
     */
    public Map<ForgedMethod, ForgedMethod> getForgedMethodsUnderCreation() {
        return forgedMethodsUnderCreation;
    }

    public TypeElement getMapperTypeElement() {
        return mapperTypeElement;
    }

    public List<SourceMethod> getSourceModel() {
        return sourceModel;
    }

    public List<MapperReference> getMapperReferences() {
        return mapperReferences;
    }

    public TypeFactory getTypeFactory() {
        return typeFactory;
    }

    public Elements getElementUtils() {
        return elementUtils;
    }

    public Types getTypeUtils() {
        return typeUtils;
    }

    public FormattingMessager getMessager() {
        return messager;
    }

    public AccessorNamingUtils getAccessorNaming() {
        return accessorNaming;
    }

    public Options getOptions() {
        return options;
    }

    public MappingResolver getMappingResolver() {
        return mappingResolver;
    }

    public List<MappingMethod> getMappingsToGenerate() {
        return mappingsToGenerate;
    }

    public List<String> getReservedNames() {
        Set<String> nameSet = new HashSet<>();
        for ( MappingMethod method : mappingsToGenerate ) {
            nameSet.add( method.getName() );
        }
        // add existing names
        for ( SourceMethod method : sourceModel) {
            if ( method.isAbstract() ) {
                nameSet.add( method.getName() );
            }
        }
        return new ArrayList<>( nameSet );
    }

    public MappingMethod getExistingMappingMethod(MappingMethod newMappingMethod) {
        MappingMethod existingMappingMethod = null;
        for ( MappingMethod mappingMethod : mappingsToGenerate ) {
            if ( newMappingMethod.equals( mappingMethod ) ) {
                existingMappingMethod = mappingMethod;
                break;
            }
        }
        return existingMappingMethod;
    }

    public Set<SupportingMappingMethod> getUsedSupportedMappings() {
        return mappingResolver.getUsedSupportedMappings();
    }

    /**
     * @param sourceType from which an automatic sub-mapping needs to be generated
     * @param targetType to which an automatic sub-mapping needs to be generated
     *
     * @return {@code true} if MapStruct is allowed to try and generate an automatic sub-mapping between the
     * source and target {@link Type}
     */
    public boolean canGenerateAutoSubMappingBetween(Type sourceType, Type targetType) {
        return canGenerateAutoSubMappingFor( sourceType ) && canGenerateAutoSubMappingFor( targetType );
    }

    /**
     * @param type that MapStruct wants to use to genrate an autoamtic sub-mapping for/from
     *
     * @return {@code true} if the type is not excluded from the {@link MappingExclusionProvider}
     */
    private boolean canGenerateAutoSubMappingFor(Type type) {
        return type.getTypeElement() != null && !SUB_MAPPING_EXCLUSION_PROVIDER.isExcluded( type.getTypeElement() );
    }
}
