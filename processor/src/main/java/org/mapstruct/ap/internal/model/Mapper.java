/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.SortedSet;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

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

    private final boolean customPackage;
    private final boolean customImplName;
    private final List<MapperReference> referencedMappers;
    private Decorator decorator;

    @SuppressWarnings( "checkstyle:parameternumber" )
    private Mapper(TypeFactory typeFactory, String packageName, String name, String superClassName,
                   String interfacePackage, String interfaceName, boolean customPackage, boolean customImplName,
                   List<MappingMethod> methods, Options options, VersionInformation versionInformation,
                   Accessibility accessibility, List<MapperReference> referencedMappers, Decorator decorator,
                   SortedSet<Type> extraImportedTypes) {

        super(
            typeFactory,
            packageName,
            name,
            superClassName,
            interfacePackage,
            interfaceName,
            methods,
            referencedMappers,
            options,
            versionInformation,
            accessibility,
            extraImportedTypes,
            null
        );
        this.customPackage = customPackage;
        this.customImplName = customImplName;

        this.referencedMappers = referencedMappers;
        this.decorator = decorator;
    }

    public static class Builder {

        private TypeFactory typeFactory;
        private TypeElement element;
        private List<MappingMethod> mappingMethods;
        private List<MapperReference> mapperReferences;
        private SortedSet<Type> extraImportedTypes;

        private Elements elementUtils;
        private Options options;
        private VersionInformation versionInformation;
        private Decorator decorator;
        private String implName;
        private boolean customName;
        private String implPackage;
        private boolean customPackage;

        public Builder element(TypeElement element) {
            this.element = element;
            return this;
        }

        public Builder mappingMethods(List<MappingMethod> mappingMethods) {
            this.mappingMethods = mappingMethods;
            return this;
        }

        public Builder mapperReferences(List<MapperReference> mapperReferences) {
            this.mapperReferences = mapperReferences;
            return this;
        }

        public Builder options(Options options) {
            this.options = options;
            return this;
        }

        public Builder versionInformation(VersionInformation versionInformation) {
            this.versionInformation = versionInformation;
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder elementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Builder decorator(Decorator decorator) {
            this.decorator = decorator;
            return this;
        }

        public Builder extraImports(SortedSet<Type> extraImportedTypes) {
            this.extraImportedTypes = extraImportedTypes;
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

        public Mapper build() {
            String implementationName = implName.replace( CLASS_NAME_PLACEHOLDER, element.getSimpleName() ) +
                    ( decorator == null ? "" : "_" );

            String elementPackage = elementUtils.getPackageOf( element ).getQualifiedName().toString();
            String packageName = implPackage.replace( PACKAGE_NAME_PLACEHOLDER, elementPackage );

            return new Mapper(
                typeFactory,
                packageName,
                implementationName,
                element.getKind() != ElementKind.INTERFACE ? element.getSimpleName().toString() : null,
                elementPackage,
                element.getKind() == ElementKind.INTERFACE ? element.getSimpleName().toString() : null,
                customPackage,
                customName,
                mappingMethods,
                options,
                versionInformation,
                Accessibility.fromModifiers( element.getModifiers() ),
                mapperReferences,
                decorator,
                extraImportedTypes
            );
        }
    }

    public List<MapperReference> getReferencedMappers() {
        return referencedMappers;
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
    public CompilationUnit getAst(Context context) {
        CompilationUnit ast = new CompilationUnit( getPackageName() );

        for ( Type importType : getImportTypes() ) {
            ast.addImport( importType.getImportName() );
        }

        ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration( EnumSet.of( Modifier.PUBLIC ), false, getName() );
        type.setImplementedTypes( NodeList.<ClassOrInterfaceType>nodeList( new ClassOrInterfaceType( getInterfaceName() ) ) );

        NormalAnnotationExpr generated = new NormalAnnotationExpr();
        generated.setName( "Generated" );
        generated.addPair( "value", "\"org.mapstruct.ap.MappingProcessor\"" );
        generated.addPair( "date", "\"" + new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" ).format( new Date() ) + "\"" );
        generated.addPair( "comments", "\"version: " + getVersionInformation().getMapStructVersion() + ", compiler: " + getVersionInformation().getCompiler() + ", environment: Java " + getVersionInformation().getRuntimeVersion() + "\"" );

        type.setAnnotations( NodeList.<AnnotationExpr>nodeList( generated ) );

        for ( MappingMethod mappingMethod : getMethods() ) {
            type.addMember( (BodyDeclaration<?>) mappingMethod.getAst( context ) );
        }

        ast.addType( type );

        return ast;
    }


    @Override
    protected String getTemplateName() {
        return getTemplateNameForClass( GeneratedType.class );
    }
}
