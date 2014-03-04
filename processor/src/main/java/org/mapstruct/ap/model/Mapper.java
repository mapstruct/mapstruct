/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.List;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.TypeFactory;

/**
 * Represents a type implementing a mapper interface (annotated with {@code @Mapper}). This is the root object of the
 * mapper model.
 *
 * @author Gunnar Morling
 */
public class Mapper extends GeneratedType {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private final List<MapperReference> referencedMappers;

    private Mapper(TypeFactory typeFactory, String packageName, String name, String superClassName,
                   String interfaceName,
                   List<MappingMethod> methods, boolean suppressGeneratorTimestamp, Accessibility accessibility,
                   List<MapperReference> referencedMappers) {

        super(
            typeFactory,
            packageName,
            name,
            superClassName,
            interfaceName,
            methods,
            referencedMappers,
            suppressGeneratorTimestamp,
            accessibility
        );

        this.referencedMappers = referencedMappers;
    }

    public static class Builder {

        private TypeFactory typeFactory;
        private TypeElement element;
        private List<MappingMethod> mappingMethods;
        private List<MapperReference> mapperReferences;

        private Elements elementUtils;
        private boolean suppressGeneratorTimestamp;

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

        public Builder suppressGeneratorTimestamp(boolean suppressGeneratorTimestamp) {
            this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
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

        public Mapper build() {
            String implementationName = element.getSimpleName() + IMPLEMENTATION_SUFFIX;

            return new Mapper(
                typeFactory,
                elementUtils.getPackageOf( element ).getQualifiedName().toString(),
                implementationName,
                element.getKind() != ElementKind.INTERFACE ? element.getSimpleName().toString() : null,
                element.getKind() == ElementKind.INTERFACE ? element.getSimpleName().toString() : null,
                mappingMethods,
                suppressGeneratorTimestamp,
                Accessibility.fromModifiers( element.getModifiers() ),
                mapperReferences
            );
        }
    }

    public List<MapperReference> getReferencedMappers() {
        return referencedMappers;
    }

    @Override
    protected String getTemplateName() {
        return GeneratedType.class.getName() + ".ftl";
    }
}
