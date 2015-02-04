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
package org.mapstruct.ap.model;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.option.Options;
import org.mapstruct.ap.prism.DecoratedWithPrism;
import org.mapstruct.ap.version.VersionInformation;

/**
 * Represents a decorator applied to a generated mapper type.
 *
 * @author Gunnar Morling
 */
public class Decorator extends GeneratedType {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    public static class Builder {

        private Elements elementUtils;
        private TypeFactory typeFactory;
        private TypeElement mapperElement;
        private DecoratedWithPrism decoratorPrism;
        private List<MappingMethod> methods;
        private Options options;
        private VersionInformation versionInformation;
        private boolean hasDelegateConstructor;

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

        public Decorator build() {
            Type decoratorType = typeFactory.getType( decoratorPrism.value() );
            DecoratorConstructor decoratorConstructor = new DecoratorConstructor(
                        mapperElement.getSimpleName().toString() + IMPLEMENTATION_SUFFIX,
                        mapperElement.getSimpleName().toString() + "Impl_",
                        hasDelegateConstructor );


            return new Decorator(
                typeFactory,
                elementUtils.getPackageOf( mapperElement ).getQualifiedName().toString(),
                mapperElement.getSimpleName().toString() + IMPLEMENTATION_SUFFIX,
                decoratorType.getName(),
                mapperElement.getKind() == ElementKind.INTERFACE ? mapperElement.getSimpleName().toString() : null,
                methods,
                Arrays.asList(  new Field( typeFactory.getType( mapperElement ), "delegate", true ) ) ,
                options,
                versionInformation,
                Accessibility.fromModifiers( mapperElement.getModifiers() ),
                decoratorConstructor
            );
        }
    }

    @SuppressWarnings( "checkstyle:parameternumber" )
    private Decorator(TypeFactory typeFactory, String packageName, String name, String superClassName,
                     String interfaceName, List<MappingMethod> methods, List<? extends Field> fields,
                     Options options, VersionInformation versionInformation, Accessibility accessibility,
                     DecoratorConstructor decoratorConstructor) {
        super(
            typeFactory,
            packageName,
            name,
            superClassName,
            interfaceName,
            methods,
            fields,
            options,
            versionInformation,
            accessibility,
            new TreeSet<Type>(),
            decoratorConstructor
        );
    }

    @Override
    protected String getTemplateName() {
        return GeneratedType.class.getName() + ".ftl";
    }
}
