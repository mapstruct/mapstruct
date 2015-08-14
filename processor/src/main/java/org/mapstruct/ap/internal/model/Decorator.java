/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.prism.DecoratedWithPrism;
import org.mapstruct.ap.internal.version.VersionInformation;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;

/**
 * Represents a decorator applied to a generated mapper type.
 *
 * @author Gunnar Morling
 */
public class Decorator extends GeneratedType {

    public static class Builder {

        private Elements elementUtils;
        private TypeFactory typeFactory;
        private TypeElement mapperElement;
        private DecoratedWithPrism decoratorPrism;
        private List<MappingMethod> methods;
        private Options options;
        private VersionInformation versionInformation;
        private boolean hasDelegateConstructor;
        private String implName;
        private String implPackage;
        private SortedSet<Type> extraImportedTypes;

        public Builder elementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder mapperElement(TypeElement mapperElement) {
            this.mapperElement = mapperElement;
            return this;
        }

        public Builder decoratorPrism(DecoratedWithPrism decoratorPrism) {
            this.decoratorPrism = decoratorPrism;
            return this;
        }

        public Builder methods(List<MappingMethod> methods) {
            this.methods = methods;
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

        public Builder extraImports(SortedSet<Type> extraImportedTypes) {
            this.extraImportedTypes = extraImportedTypes;
            return this;
        }

        public Decorator build() {
            String implementationName = implName.replace( Mapper.CLASS_NAME_PLACEHOLDER,
                                                          mapperElement.getSimpleName() );

            Type decoratorType = typeFactory.getType( decoratorPrism.value() );
            DecoratorConstructor decoratorConstructor = new DecoratorConstructor(
                        implementationName,
                        implementationName + "_",
                        hasDelegateConstructor );


            String elementPackage = elementUtils.getPackageOf( mapperElement ).getQualifiedName().toString();
            String packageName = implPackage.replace( Mapper.PACKAGE_NAME_PLACEHOLDER, elementPackage );

            return new Decorator(
                typeFactory,
                packageName,
                implementationName,
                decoratorType,
                elementPackage,
                mapperElement.getKind() == ElementKind.INTERFACE ? mapperElement.getSimpleName().toString() : null,
                methods,
                Arrays.asList(  new Field( typeFactory.getType( mapperElement ), "delegate", true ) ) ,
                options,
                versionInformation,
                Accessibility.fromModifiers( mapperElement.getModifiers() ),
                extraImportedTypes,
                decoratorConstructor
            );
        }
    }

    private final Type decoratorType;

    @SuppressWarnings( "checkstyle:parameternumber" )
    private Decorator(TypeFactory typeFactory, String packageName, String name, Type decoratorType,
                      String interfacePackage, String interfaceName, List<MappingMethod> methods,
                      List<? extends Field> fields, Options options, VersionInformation versionInformation,
                      Accessibility accessibility, SortedSet<Type> extraImports,
                      DecoratorConstructor decoratorConstructor) {
        super(
            typeFactory,
            packageName,
            name,
            decoratorType.getName(),
            interfacePackage,
            interfaceName,
            methods,
            fields,
            options,
            versionInformation,
            accessibility,
            extraImports,
            decoratorConstructor
        );

        this.decoratorType = decoratorType;
    }

    @Override
    public SortedSet<Type> getImportTypes() {
        SortedSet<Type> importTypes = super.getImportTypes();
        addWithDependents( importTypes, decoratorType );
        return importTypes;
    }

    @Override
    protected String getTemplateName() {
        return getTemplateNameForClass( GeneratedType.class );
    }
}
