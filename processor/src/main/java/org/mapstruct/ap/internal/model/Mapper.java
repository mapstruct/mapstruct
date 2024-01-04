/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * Represents a type implementing a mapper interface (annotated with {@code @Mapper}). This is the root object of the
 * mapper model.
 *
 * @author Gunnar Morling
 */
public class Mapper extends GeneratedType {

    static final String CLASS_NAME_PLACEHOLDER = "<CLASS_NAME>";
    static final String PACKAGE_NAME_PLACEHOLDER = "<PACKAGE_NAME>";
    static final String DEFAULT_IMPLEMENTATION_CLASS = CLASS_NAME_PLACEHOLDER + "Impl";
    static final String DEFAULT_IMPLEMENTATION_PACKAGE = PACKAGE_NAME_PLACEHOLDER;

    public static class Builder extends GeneratedTypeBuilder<Builder> {

        private TypeElement element;
        private List<Field> fields;
        private Set<SupportingConstructorFragment> fragments;

        private Decorator decorator;
        private String implName;
        private boolean customName;
        private String implPackage;
        private boolean customPackage;
        private boolean suppressGeneratorTimestamp;
        private Set<Annotation> customAnnotations;
        private Javadoc javadoc;

        public Builder() {
            super( Builder.class );
        }

        public Builder element(TypeElement element) {
            this.element = element;
            return this;
        }

        public Builder fields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public Builder constructorFragments(Set<SupportingConstructorFragment>  fragments) {
            this.fragments = fragments;
            return this;
        }

        public Builder additionalAnnotations(Set<Annotation> customAnnotations) {
            this.customAnnotations = customAnnotations;
            return this;
        }

        public Builder decorator(Decorator decorator) {
            this.decorator = decorator;
            return this;
        }

        public Builder implName(String implName) {
            this.implName = implName;
            this.customName = !DEFAULT_IMPLEMENTATION_CLASS.equals( this.implName );
            return this;
        }

        public Builder implPackage(String implPackage) {
            this.implPackage = implPackage;
            this.customPackage = !DEFAULT_IMPLEMENTATION_PACKAGE.equals( this.implPackage );
            return this;
        }

        public Builder suppressGeneratorTimestamp(boolean suppressGeneratorTimestamp) {
            this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
            return this;
        }

        public Builder javadoc(Javadoc javadoc) {
            this.javadoc = javadoc;
            return this;
        }

        public Mapper build() {
            String implementationName = implName.replace( CLASS_NAME_PLACEHOLDER, getFlatName( element ) ) +
                ( decorator == null ? "" : "_" );

            String elementPackage = elementUtils.getPackageOf( element ).getQualifiedName().toString();
            String packageName = implPackage.replace( PACKAGE_NAME_PLACEHOLDER, elementPackage );
            Constructor constructor = null;
            if ( !fragments.isEmpty() ) {
                constructor = new NoArgumentConstructor( implementationName, fragments );
            }

            Type definitionType = typeFactory.getType( element );

            return new Mapper(
                typeFactory,
                packageName,
                implementationName,
                definitionType,
                customPackage,
                customName,
                customAnnotations,
                methods,
                options,
                versionInformation,
                suppressGeneratorTimestamp,
                Accessibility.fromModifiers( element.getModifiers() ),
                fields,
                constructor,
                decorator,
                extraImportedTypes,
                javadoc
            );
        }

    }

    private final boolean customPackage;
    private final boolean customImplName;
    private Decorator decorator;
    private final Javadoc javadoc;

    @SuppressWarnings( "checkstyle:parameternumber" )
    private Mapper(TypeFactory typeFactory, String packageName, String name,
                   Type mapperDefinitionType,
                   boolean customPackage, boolean customImplName, Set<Annotation> customAnnotations,
                   List<MappingMethod> methods, Options options, VersionInformation versionInformation,
                   boolean suppressGeneratorTimestamp,
                   Accessibility accessibility, List<Field> fields, Constructor constructor,
                   Decorator decorator, SortedSet<Type> extraImportedTypes, Javadoc javadoc ) {

        super(
            typeFactory,
            packageName,
            name,
            mapperDefinitionType,
            methods,
            fields,
            options,
            versionInformation,
            suppressGeneratorTimestamp,
            accessibility,
            extraImportedTypes,
            constructor
        );
        this.customPackage = customPackage;
        this.customImplName = customImplName;
        customAnnotations.forEach( this::addAnnotation );

        this.decorator = decorator;

        this.javadoc = javadoc;
    }

    public Decorator getDecorator() {
        return decorator;
    }

    public void removeDecorator() {
        this.decorator = null;
    }

    public boolean hasCustomImplementation() {
        return customImplName || customPackage;
    }

    @Override
    public Javadoc getJavadoc() {
        return javadoc;
    }

    @Override
    protected String getTemplateName() {
        return getTemplateNameForClass( GeneratedType.class );
    }

    /**
     * Returns the same as {@link Class#getName()} but without the package declaration.
     *
     * @param element the element that should be flattened
     *
     * @return the flat name for the type element
     */
    public static String getFlatName(TypeElement element) {
        if (!(element.getEnclosingElement() instanceof TypeElement)) {
            return element.getSimpleName().toString();
        }
        StringBuilder nameBuilder = new StringBuilder( element.getSimpleName().toString() );
        for (Element enclosing = element.getEnclosingElement(); enclosing instanceof TypeElement; enclosing =
                enclosing.getEnclosingElement()) {
            nameBuilder.insert( 0, '$' );
            nameBuilder.insert( 0, enclosing.getSimpleName().toString() );
        }
        return nameBuilder.toString();
    }
}
