/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.mapperfactory;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import org.mapstruct.ap.internal.model.Constructor;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 *
 * @author Sjaak Derksen
 */
public class MapperFactory extends GeneratedType {



    public static class Builder {

        private List<MapperFactoryMethod> factoryMethods;
        private VersionInformation versionInformation;
        private String implNameProperty;
        private String implPackageProperty;
        private TypeElement element;
        private Elements elementUtils;
        private TypeFactory typeFactory;
        private Options options;

        public Builder element(TypeElement element) {
            this.element = element;
            return this;
        }

        public Builder elementUtils(Elements elementUtils) {
            this.elementUtils = elementUtils;
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder options(Options options) {
            this.options = options;
            return this;
        }

        public Builder factoryMethods(List<MapperFactoryMethod> factoryMethods) {
            this.factoryMethods = factoryMethods;
            return this;
        }

        public Builder versionInformation(VersionInformation versionInformation) {
            this.versionInformation = versionInformation;
            return this;
        }

        public Builder implNameProperty(String implName) {
            this.implNameProperty = implName;
            return this;
        }

        public Builder implPackageProperty(String implPackage) {
            this.implPackageProperty = implPackage;
            return this;
        }

        public MapperFactory build() {
            String interfacePackageName = elementUtils.getPackageOf( element ).getQualifiedName().toString();
            return new MapperFactory(
                typeFactory,
                getImplementationPackageName( implPackageProperty, interfacePackageName ),
                getImplementationName( implNameProperty, element.getSimpleName().toString() ),
                interfacePackageName,
                element.getSimpleName().toString(),
                factoryMethods,
                options,
                versionInformation

            );
        }
    }

    public MapperFactory(
        TypeFactory typeFactory,
        String packageName,
        String name,
        String interfacePackage,
        String interfaceName,
        List<MapperFactoryMethod> methods,
        Options options,
        VersionInformation versionInformation) {
        super( typeFactory,
            packageName,
            name,
            null,
            interfacePackage,
            interfaceName,
            methods,
            Collections.<Field>emptyList(),
            options,
            versionInformation,
            Accessibility.PUBLIC,
            new TreeSet<Type>(),
            Collections.<Constructor>emptyList() );
    }

    @Override
    protected String getTemplateName() {
        return getTemplateNameForClass( GeneratedType.class );
    }
}
