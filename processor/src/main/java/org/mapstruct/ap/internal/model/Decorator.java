/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.gem.DecoratedWithGem;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * Represents a decorator applied to a generated mapper type.
 *
 * @author Gunnar Morling
 */
public class Decorator extends GeneratedType {

    public static class Builder extends GeneratedTypeBuilder<Builder> {

        private TypeElement mapperElement;
        private DecoratedWithGem decorator;

        private boolean hasDelegateConstructor;
        private String implName;
        private String implPackage;
        private boolean suppressGeneratorTimestamp;
        private Set<Annotation> customAnnotations;

        public Builder() {
            super( Builder.class );
        }

        public Builder mapperElement(TypeElement mapperElement) {
            this.mapperElement = mapperElement;
            return this;
        }

        public Builder decoratedWith(DecoratedWithGem decoratedGem) {
            this.decorator = decoratedGem;
            return this;
        }

        public Builder hasDelegateConstructor(boolean hasDelegateConstructor) {
            this.hasDelegateConstructor = hasDelegateConstructor;
            return this;
        }

        public Builder implName(String implName) {
            this.implName = "default".equals( implName ) ? Mapper.DEFAULT_IMPLEMENTATION_CLASS : implName;
            return this;
        }

        public Builder implPackage(String implPackage) {
            this.implPackage = "default".equals( implPackage ) ? Mapper.DEFAULT_IMPLEMENTATION_PACKAGE : implPackage;
            return this;
        }

        public Builder suppressGeneratorTimestamp(boolean suppressGeneratorTimestamp) {
            this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
            return this;
        }

        public Builder additionalAnnotations(Set<Annotation> customAnnotations) {
            this.customAnnotations = customAnnotations;
            return this;
        }

        public Decorator build() {
            String implementationName = implName.replace( Mapper.CLASS_NAME_PLACEHOLDER,
                Mapper.getFlatName( mapperElement ) );

            Type decoratorType = typeFactory.getType( decorator.value().get() );
            DecoratorConstructor decoratorConstructor = new DecoratorConstructor(
                implementationName,
                implementationName + "_",
                hasDelegateConstructor );


            Type mapperType = typeFactory.getType( mapperElement );
            String elementPackage = mapperType.getPackageName();
            String packageName = implPackage.replace( Mapper.PACKAGE_NAME_PLACEHOLDER, elementPackage );

            return new Decorator(
                typeFactory,
                packageName,
                implementationName,
                decoratorType,
                mapperType,
                methods,
                options,
                versionInformation,
                suppressGeneratorTimestamp,
                Accessibility.fromModifiers( mapperElement.getModifiers() ),
                extraImportedTypes,
                decoratorConstructor,
                customAnnotations
            );
        }
    }

    private final Type decoratorType;
    private final Type mapperType;

    @SuppressWarnings( "checkstyle:parameternumber" )
    private Decorator(TypeFactory typeFactory, String packageName, String name, Type decoratorType,
                      Type mapperType,
                      List<MappingMethod> methods,
                      Options options, VersionInformation versionInformation,
                      boolean suppressGeneratorTimestamp,
                      Accessibility accessibility, SortedSet<Type> extraImports,
                      DecoratorConstructor decoratorConstructor,
                      Set<Annotation> customAnnotations) {
        super(
            typeFactory,
            packageName,
            name,
            decoratorType,
            methods,
            Arrays.asList( new Field( mapperType, "delegate", true ) ),
            options,
            versionInformation,
            suppressGeneratorTimestamp,
            accessibility,
            extraImports,
            decoratorConstructor
        );

        this.decoratorType = decoratorType;
        this.mapperType = mapperType;

        // Add custom annotations
        if ( customAnnotations != null ) {
            customAnnotations.forEach( this::addAnnotation );
        }
    }

    @Override
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importTypes = super.getImportTypes();
        // DecoratorType needs special handling in case it is nested
        // calling addIfImportRequired is not the most correct approach since it would
        // lead to checking if the type is to be imported and that would be false
        // since the Decorator is a nested class within the Mapper.
        // However, when generating the Decorator this is not needed,
        // because the Decorator is a top level class itself
        // In a nutshell creating the Decorator should have its own ProcessorContext, but it doesn't
        if ( decoratorType.getPackageName().equalsIgnoreCase( getPackageName() ) ) {
            if ( decoratorType.getTypeElement() != null &&
                decoratorType.getTypeElement().getNestingKind().isNested() ) {
                importTypes.add( decoratorType );
            }
        }
        else {
            importTypes.add( decoratorType );
        }
        return importTypes;
    }

    @Override
    protected String getTemplateName() {
        return getTemplateNameForClass( GeneratedType.class );
    }

    public Type getMapperType() {
        return mapperType;
    }
}
