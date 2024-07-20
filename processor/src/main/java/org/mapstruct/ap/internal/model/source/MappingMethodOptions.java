/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import static org.mapstruct.ap.internal.model.source.MappingOptions.getMappingTargetNamesBy;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;

import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/**
 * Encapsulates all options specifiable on a mapping method
 *
 * @author Andreas Gudian
 */
public class MappingMethodOptions {
    private static final MappingMethodOptions EMPTY = new MappingMethodOptions(
        null,
        Collections.emptySet(),
        null,
        null,
        null,
        null,
        Collections.emptyList(),
        Collections.emptySet(),
        null
    );

    private MapperOptions mapper;
    private Set<MappingOptions> mappings;
    private IterableMappingOptions iterableMapping;
    private MapMappingOptions mapMapping;
    private BeanMappingOptions beanMapping;
    private EnumMappingOptions enumMappingOptions;
    private List<ValueMappingOptions> valueMappings;
    private boolean fullyInitialized;
    private Set<SubclassMappingOptions> subclassMappings;

    private SubclassValidator subclassValidator;

    public MappingMethodOptions(MapperOptions mapper, Set<MappingOptions> mappings,
                                IterableMappingOptions iterableMapping,
                                MapMappingOptions mapMapping, BeanMappingOptions beanMapping,
                                EnumMappingOptions enumMappingOptions,
                                List<ValueMappingOptions> valueMappings,
                                Set<SubclassMappingOptions> subclassMappings, SubclassValidator subclassValidator) {
        this.mapper = mapper;
        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;
        this.beanMapping = beanMapping;
        this.enumMappingOptions = enumMappingOptions;
        this.valueMappings = valueMappings;
        this.subclassMappings = subclassMappings;
        this.subclassValidator = subclassValidator;
    }

    /**
     * creates empty mapping options
     *
     * @return empty mapping options
     */
    public static MappingMethodOptions empty() {
        return EMPTY;
    }

    /**
     * @return the {@link MappingOptions}s configured for this method, keyed by target property name. Only for enum
     * mapping methods a target will be mapped by several sources.
     */
    public Set<MappingOptions> getMappings() {
        return mappings;
    }

    public IterableMappingOptions getIterableMapping() {
        return iterableMapping;
    }

    public MapMappingOptions getMapMapping() {
        return mapMapping;
    }

    public BeanMappingOptions getBeanMapping() {
        return beanMapping;
    }

    public EnumMappingOptions getEnumMappingOptions() {
        return enumMappingOptions;
    }

    public List<ValueMappingOptions> getValueMappings() {
        return valueMappings;
    }

    public Set<SubclassMappingOptions> getSubclassMappings() {
        return subclassMappings;
    }

    public void setIterableMapping(IterableMappingOptions iterableMapping) {
        this.iterableMapping = iterableMapping;
    }

    public void setMapMapping(MapMappingOptions mapMapping) {
        this.mapMapping = mapMapping;
    }

    public void setBeanMapping(BeanMappingOptions beanMapping) {
        this.beanMapping = beanMapping;
    }

    public void setEnumMappingOptions(EnumMappingOptions enumMappingOptions) {
        this.enumMappingOptions = enumMappingOptions;
    }

    public void setValueMappings(List<ValueMappingOptions> valueMappings) {
        this.valueMappings = valueMappings;
    }

    public MapperOptions getMapper() {
        return mapper;
    }

    /**
     * @return the {@code true}, iff the options have been fully initialized by applying all available inheritance
     * options
     */
    public boolean isFullyInitialized() {
        return fullyInitialized;
    }

    public void markAsFullyInitialized() {
        this.fullyInitialized = true;
    }

    /**
     * Merges in all the mapping options configured, giving the already defined options precedence.
     *
     * @param sourceMethod the method which inherits the options.
     * @param templateMethod the template method with the options to inherit, may be {@code null}
     * @param isInverse if {@code true}, the specified options are from an inverse method
     * @param annotationMirror the annotation on which the compile errors will be shown.
     */
    public void applyInheritedOptions(SourceMethod sourceMethod, SourceMethod templateMethod, boolean isInverse,
                                      AnnotationMirror annotationMirror) {
        MappingMethodOptions templateOptions = templateMethod.getOptions();
        if ( null != templateOptions ) {
            if ( !getIterableMapping().hasAnnotation() && templateOptions.getIterableMapping().hasAnnotation() ) {
                setIterableMapping( templateOptions.getIterableMapping() );
            }

            if ( !getMapMapping().hasAnnotation() && templateOptions.getMapMapping().hasAnnotation() ) {
                setMapMapping( templateOptions.getMapMapping() );
            }

            if ( !getBeanMapping().hasAnnotation() && templateOptions.getBeanMapping().hasAnnotation() ) {
                setBeanMapping( BeanMappingOptions.forInheritance( templateOptions.getBeanMapping( ), isInverse ) );
            }

            if ( !getEnumMappingOptions().hasAnnotation() && templateOptions.getEnumMappingOptions().hasAnnotation() ) {
                EnumMappingOptions newEnumMappingOptions;
                if ( isInverse ) {
                    newEnumMappingOptions = templateOptions.getEnumMappingOptions().inverse();
                }
                else {
                    newEnumMappingOptions = templateOptions.getEnumMappingOptions();
                }
                setEnumMappingOptions( newEnumMappingOptions );
            }

            if ( getValueMappings() == null ) {
                if ( templateOptions.getValueMappings() != null ) {
                    // there were no mappings, so the inherited mappings are the new ones
                    setValueMappings( templateOptions.getValueMappings() );
                }
                else {
                    setValueMappings( Collections.emptyList() );
                }
            }
            else {
                if ( templateOptions.getValueMappings() != null ) {
                    // if there are also inherited mappings, we inverse and add them.
                    for ( ValueMappingOptions inheritedValueMapping : templateOptions.getValueMappings() ) {
                        ValueMappingOptions valueMapping =
                            isInverse ? inheritedValueMapping.inverse() : inheritedValueMapping;
                        if ( valueMapping != null
                            && !getValueMappings().contains(  valueMapping ) ) {
                            getValueMappings().add( valueMapping );
                        }
                    }
                }
            }

            if ( isInverse ) {
                List<SubclassMappingOptions> inheritedMappings = SubclassMappingOptions.copyForInverseInheritance(
                                              templateOptions.getSubclassMappings(),
                                              getBeanMapping() );
                addAllNonRedefined( sourceMethod, annotationMirror, inheritedMappings );
            }
            else if ( methodsHaveIdenticalSignature( templateMethod, sourceMethod ) ) {
                List<SubclassMappingOptions> inheritedMappings =
                    SubclassMappingOptions
                                          .copyForInheritance(
                                              templateOptions.getSubclassMappings(),
                                              getBeanMapping() );
                addAllNonRedefined( sourceMethod, annotationMirror, inheritedMappings );
            }

            Set<MappingOptions> newMappings = new LinkedHashSet<>();
            for ( MappingOptions mapping : templateOptions.getMappings() ) {
                if ( isInverse ) {
                    if ( mapping.canInverse() ) {
                        newMappings.add( mapping.copyForInverseInheritance( templateMethod, getBeanMapping() ) );
                    }
                }
                else {
                    newMappings.add( mapping.copyForForwardInheritance( templateMethod, getBeanMapping() ) );
                }
            }

            // now add all (does not override duplicates and leaves original mappings)
            addAllNonRedefined( newMappings );

            // filter new mappings
            filterNestedTargetIgnores( mappings );
        }
    }

    private boolean methodsHaveIdenticalSignature(SourceMethod templateMethod, SourceMethod sourceMethod) {
        return parametersAreOfIdenticalTypeAndOrder( templateMethod, sourceMethod )
            && resultTypeIsTheSame( templateMethod, sourceMethod );
    }

    private boolean parametersAreOfIdenticalTypeAndOrder(SourceMethod templateMethod, SourceMethod sourceMethod) {
        if (templateMethod.getParameters().size() != sourceMethod.getParameters().size()) {
            return false;
        }
        for ( int i = 0; i < templateMethod.getParameters().size(); i++ ) {
            if (!templateMethod.getParameters().get( i ).getType().equals(
                                       sourceMethod.getParameters().get( i ).getType() ) ) {
                return false;
            }
        }
        return true;
    }

    private boolean resultTypeIsTheSame(SourceMethod templateMethod, SourceMethod sourceMethod) {
        return templateMethod.getResultType().equals( sourceMethod.getResultType() );
    }

    private void addAllNonRedefined(SourceMethod sourceMethod, AnnotationMirror annotationMirror,
                                    List<SubclassMappingOptions> inheritedMappings) {
        Set<SubclassMappingOptions> redefinedSubclassMappings = new HashSet<>( subclassMappings );
        for ( SubclassMappingOptions subclassMappingOption : inheritedMappings ) {
            if ( !redefinedSubclassMappings.contains( subclassMappingOption ) ) {
                if ( subclassValidator.isValidUsage(
                                          sourceMethod.getExecutable(),
                                          annotationMirror,
                    subclassMappingOption.getSource() ) ) {
                    subclassMappings.add( subclassMappingOption );
                }
            }
        }
    }

    private void addAllNonRedefined(Set<MappingOptions> inheritedMappings) {
        // We are only adding the targets here since this mappings have already been reversed
        Set<String> redefinedTargets = new HashSet<>();
        for ( MappingOptions redefinedMappings : mappings ) {
            if ( redefinedMappings.getTargetName() != null ) {
                redefinedTargets.add( redefinedMappings.getTargetName() );
            }
        }
        for ( MappingOptions inheritedMapping : inheritedMappings ) {
            if ( inheritedMapping.isIgnored()
                || !isRedefined( redefinedTargets, inheritedMapping.getTargetName() )
            ) {
                mappings.add( inheritedMapping );
            }
        }
    }

    private boolean isRedefined(Set<String> redefinedNames, String inheritedName ) {
        if ( inheritedName != null ) {
            for ( String redefinedName : redefinedNames ) {
                if ( elementsAreContainedIn( inheritedName, redefinedName ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean elementsAreContainedIn( String redefinedName, String inheritedName ) {
        if ( inheritedName != null && redefinedName.startsWith( inheritedName ) ) {
            // it is possible to redefine an exact matching source name, because the same source can be mapped to
            // multiple targets. It is not possible for target, but caught by the Set and equals method in
            // MappingOptions. SourceName == null also could hint at redefinition
            if ( redefinedName.length() > inheritedName.length() ) {
                // redefined.lenght() > inherited.length(), first following character should be separator
                return '.' == redefinedName.charAt( inheritedName.length() );
            }
        }
        return false;
    }

    public void applyIgnoreAll(SourceMethod method, TypeFactory typeFactory,
                               FormattingMessager messager) {
        CollectionMappingStrategyGem cms = method.getOptions().getMapper().getCollectionMappingStrategy();
        Type writeType = method.getResultType();
        if ( !method.isUpdateMethod() ) {
            writeType = typeFactory.effectiveResultTypeFor(
                writeType,
                method.getOptions().getBeanMapping().getBuilder()
            );
        }
        Map<String, Accessor> writeAccessors = writeType.getPropertyWriteAccessors( cms );


        for ( MappingOptions mapping : mappings ) {
            String mappedTargetProperty = getFirstTargetPropertyName( mapping );
            if ( !".".equals( mappedTargetProperty ) ) {
                // Remove the mapped target property from the write accessors
                writeAccessors.remove( mappedTargetProperty );
            }
            else {
                messager.printMessage(
                    method.getExecutable(),
                    getBeanMapping().getMirror(),
                    Message.BEANMAPPING_IGNORE_BY_DEFAULT_WITH_MAPPING_TARGET_THIS
                );
                // Nothing more to do if this is reached
                return;
            }
        }

        // The writeAccessors now contains only the accessors that should be ignored
        for ( String targetPropertyName : writeAccessors.keySet() ) {
            MappingOptions mapping = MappingOptions.forIgnore( targetPropertyName );
            mappings.add( mapping );
        }
    }

    private void filterNestedTargetIgnores( Set<MappingOptions> mappings) {

        // collect all properties to ignore, and safe their target name ( == same name as first ref target property)
        Set<String> ignored = getMappingTargetNamesBy( MappingOptions::isIgnored, mappings );
        mappings.removeIf( m -> isToBeIgnored( ignored, m ) );
    }

    private boolean isToBeIgnored(Set<String> ignored, MappingOptions mapping) {
        String[] propertyEntries = getPropertyEntries( mapping );
        return propertyEntries.length > 1 && ignored.contains( propertyEntries[ 0 ] );
    }

    private String[] getPropertyEntries( MappingOptions mapping ) {
        return mapping.getTargetName().split( "\\." );
    }

    private String getFirstTargetPropertyName(MappingOptions mapping) {
        String targetName = mapping.getTargetName();
        if ( ".".equals( targetName ) ) {
            return targetName;
        }

        return getPropertyEntries( mapping )[0];
    }

    /**
     * SubclassMappingOptions are not inherited to forged methods. They would result in an infinite loop if they were.
     *
     * @return a MappingMethodOptions without SubclassMappingOptions or SubclassValidator.
     */
    public static MappingMethodOptions getForgedMethodInheritedOptions(MappingMethodOptions options) {
        return new MappingMethodOptions(
            options.mapper,
            options.mappings,
            options.iterableMapping,
            options.mapMapping,
            BeanMappingOptions.forForgedMethods( options.beanMapping ),
            options.enumMappingOptions,
            options.valueMappings,
            Collections.emptySet(),
            null );
    }

    public static MappingMethodOptions getSubclassForgedMethodInheritedOptions(MappingMethodOptions options) {
        return new MappingMethodOptions(
            options.mapper,
            options.mappings,
            options.iterableMapping,
            options.mapMapping,
            BeanMappingOptions.forSubclassForgedMethods( options.beanMapping ),
            options.enumMappingOptions,
            options.valueMappings,
            Collections.emptySet(),
            null );
    }

}
