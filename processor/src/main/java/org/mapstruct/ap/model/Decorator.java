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
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.prism.DecoratedWithPrism;

/**
 * Represents a decorator applied to a generated mapper type.
 *
 * @author Gunnar Morling
 */
public class Decorator extends GeneratedType {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private Decorator(TypeFactory typeFactory, String packageName, String name, String superClassName,
                      String interfaceName, List<MappingMethod> methods, List<? extends ModelElement> fields,
                      boolean suppressGeneratorTimestamp, Accessibility accessibility ) {
        super(
            typeFactory,
            packageName,
            name,
            superClassName,
            interfaceName,
            methods,
            fields,
            suppressGeneratorTimestamp,
            accessibility,
            new TreeSet<Type>()
        );
    }

    public static Decorator getInstance(Elements elementUtils, TypeFactory typeFactory, TypeElement mapperElement,
                                        DecoratedWithPrism decoratorPrism, List<MappingMethod> methods,
                                        boolean hasDelegateConstructor,
                                        boolean suppressGeneratorTimestamp) {
        Type decoratorType = typeFactory.getType( decoratorPrism.value() );

        return new Decorator(
            typeFactory,
            elementUtils.getPackageOf( mapperElement ).getQualifiedName().toString(),
            mapperElement.getSimpleName().toString() + IMPLEMENTATION_SUFFIX,
            decoratorType.getName(),
            mapperElement.getKind() == ElementKind.INTERFACE ? mapperElement.getSimpleName().toString() : null,
            methods,
            Arrays.asList(
                new Field( typeFactory.getType( mapperElement ), "delegate", true ),
                new DecoratorConstructor(
                    mapperElement.getSimpleName().toString() + IMPLEMENTATION_SUFFIX,
                    mapperElement.getSimpleName().toString() + "Impl_",
                    hasDelegateConstructor
                )
            ),
            suppressGeneratorTimestamp,
            Accessibility.fromModifiers( mapperElement.getModifiers() )
        );
    }

    @Override
    protected String getTemplateName() {
        return GeneratedType.class.getName() + ".ftl";
    }
}
