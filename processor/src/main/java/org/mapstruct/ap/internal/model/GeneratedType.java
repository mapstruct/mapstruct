/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * A type generated by MapStruct, e.g. representing a mapper type.
 *
 * @author Gunnar Morling
 */
public abstract class GeneratedType extends ModelElement {

    private static final String JAVA_LANG_PACKAGE = "java.lang";

    protected abstract static class GeneratedTypeBuilder<T extends GeneratedTypeBuilder> {

        private T myself;
        protected TypeFactory typeFactory;
        protected ElementUtils elementUtils;
        protected Options options;
        protected VersionInformation versionInformation;
        protected SortedSet<Type> extraImportedTypes;

        protected List<MappingMethod> methods;

        GeneratedTypeBuilder(Class<T> selfType) {
            myself = selfType.cast( this );
        }

        public T elementUtils(ElementUtils elementUtils) {
            this.elementUtils = elementUtils;
            return myself;
        }

        public T typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return myself;
        }

        public T options(Options options) {
            this.options = options;
            return myself;
        }

        public T versionInformation(VersionInformation versionInformation) {
            this.versionInformation = versionInformation;
            return myself;
        }

        public T extraImports(SortedSet<Type> extraImportedTypes) {
            this.extraImportedTypes = extraImportedTypes;
            return myself;
        }

        public T methods(List<MappingMethod> methods) {
            this.methods = methods;
            return myself;
        }

    }

    private final String packageName;
    private final String name;
    private final Type mapperDefinitionType;

    private final List<Annotation> annotations;
    private final List<MappingMethod> methods;
    private final SortedSet<Type> extraImportedTypes;

    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final VersionInformation versionInformation;
    private final Accessibility accessibility;
    private List<Field> fields;
    private Constructor constructor;

    /**
     * Type representing the {@code @Generated} annotation
     */
    private final Type generatedType;
    private final boolean generatedTypeAvailable;

    // CHECKSTYLE:OFF
    protected GeneratedType(TypeFactory typeFactory, String packageName, String name,
                            Type mapperDefinitionType, List<MappingMethod> methods,
                            List<Field> fields, Options options, VersionInformation versionInformation,
                            boolean suppressGeneratorTimestamp,
                            Accessibility accessibility, SortedSet<Type> extraImportedTypes, Constructor constructor) {
        this.packageName = packageName;
        this.name = name;
        this.mapperDefinitionType = mapperDefinitionType;
        this.extraImportedTypes = extraImportedTypes;

        this.annotations = new ArrayList<>();
        this.methods = methods;
        this.fields = fields;

        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = options.isSuppressGeneratorVersionComment();
        this.versionInformation = versionInformation;
        this.accessibility = accessibility;

        if ( versionInformation.isSourceVersionAtLeast9() &&
            typeFactory.isTypeAvailable( "javax.annotation.processing.Generated" ) ) {
            this.generatedType = typeFactory.getType( "javax.annotation.processing.Generated" );
            this.generatedTypeAvailable = true;
        }
        else if ( typeFactory.isTypeAvailable( "javax.annotation.Generated" ) ) {
            this.generatedType = typeFactory.getType( "javax.annotation.Generated" );
            this.generatedTypeAvailable = true;
        }
        else {
            this.generatedType = null;
            this.generatedTypeAvailable = false;
        }

        this.constructor = constructor;
    }

    // CHECKSTYLE:ON

    public String getPackageName() {
        return packageName;
    }

    public boolean hasPackageName() {
        return !Strings.isEmpty( packageName );
    }

    public String getName() {
        return name;
    }

    public Type getMapperDefinitionType() {
        return mapperDefinitionType;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(Annotation annotation) {
        annotations.add( annotation );
    }

    public List<MappingMethod> getMethods() {
        return methods;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

    public boolean isGeneratedTypeAvailable() {
        return generatedTypeAvailable;
    }

    public VersionInformation getVersionInformation() {
        return versionInformation;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importedTypes = new TreeSet<>();
        addIfImportRequired( importedTypes, generatedType );

        addIfImportRequired( importedTypes, mapperDefinitionType );

        for ( MappingMethod mappingMethod : methods ) {
            for ( Type type : mappingMethod.getImportTypes() ) {
                addIfImportRequired( importedTypes, type );
            }
        }

        for ( Field field : fields ) {
            if ( field.isTypeRequiresImport() ) {
                for ( Type type : field.getImportTypes() ) {
                    addIfImportRequired( importedTypes, type );
                }
            }
        }

        for ( Annotation annotation : annotations ) {
            for ( Type type : annotation.getImportTypes() ) {
                addIfImportRequired( importedTypes, type );
            }
        }

        for ( Type extraImport : extraImportedTypes ) {
            addIfImportRequired( importedTypes, extraImport );
        }

        if ( constructor != null ) {
            for ( Type type : constructor.getImportTypes() ) {
                addIfImportRequired( importedTypes, type );
            }
        }

        return importedTypes;
    }

    public SortedSet<String> getImportTypeNames() {
        SortedSet<String> importTypeNames = new TreeSet<>();
        for ( Type type : getImportTypes() ) {
            importTypeNames.add( type.getImportName() );
        }
        return importTypeNames;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public void removeConstructor() {
        constructor = null;
    }

    public Javadoc getJavadoc() {
        return null;
    }

    protected void addIfImportRequired(Collection<Type> collection, Type typeToAdd) {
        if ( typeToAdd == null ) {
            return;
        }

        for ( Type type : typeToAdd.getImportTypes() ) {
            if ( needsImportDeclaration( type ) ) {
                collection.add( type );
            }
        }
    }

    private boolean needsImportDeclaration(Type typeToAdd) {
        if ( !typeToAdd.isToBeImported() ) {
            return false;
        }

        if ( typeToAdd.getTypeMirror().getKind() != TypeKind.DECLARED && !typeToAdd.isArrayType() ) {
            return false;
        }

        if ( typeToAdd.getPackageName() != null ) {
            if ( typeToAdd.getPackageName().equals( JAVA_LANG_PACKAGE ) ) {
                // only the types in the java.lang package are implicitly imported, the packages under java.lang
                // like java.lang.management are not.
                return false;
            }

            if ( typeToAdd.getPackageName().equals( packageName ) ) {
                if ( typeToAdd.getTypeElement() != null ) {
                    if ( !typeToAdd.getTypeElement().getNestingKind().isNested() ) {
                        return false;
                    }
                }
                else if ( typeToAdd.getComponentType() != null ) {
                    if ( !typeToAdd.getComponentType().getTypeElement().getNestingKind().isNested() ) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
