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

import java.util.Collection;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.source.BeanMapping;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.prism.BuilderPrism;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * @author Filip Hrisafov
 */
public class BuilderFinisherMethodResolver {

    private static final String DEFAULT_BUILD_METHOD_NAME = "build";

    private BuilderFinisherMethodResolver() {
    }

    public static MethodReference getBuilderFinisherMethod(Method method, BuilderType builderType,
        MappingBuilderContext ctx) {
        Collection<ExecutableElement> buildMethods = builderType.getBuildMethods();
        if ( buildMethods.isEmpty() ) {
            //If we reach this method this should never happen
            return null;
        }

        BuilderPrism builderMapping = builderMappingPrism( method, ctx );
        if ( builderMapping == null && buildMethods.size() == 1 ) {
            return MethodReference.forMethodCall( first( buildMethods ).getSimpleName().toString() );
        }
        else {
            String buildMethodPattern = DEFAULT_BUILD_METHOD_NAME;
            if ( builderMapping != null ) {
                buildMethodPattern = builderMapping.buildMethod();
            }
            for ( ExecutableElement buildMethod : buildMethods ) {
                String methodName = buildMethod.getSimpleName().toString();
                if ( methodName.matches( buildMethodPattern ) ) {
                    return MethodReference.forMethodCall( methodName );
                }
            }

            if ( builderMapping == null ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.BUILDER_NO_BUILD_METHOD_FOUND_DEFAULT,
                    buildMethodPattern,
                    builderType.getBuilder(),
                    builderType.getBuildingType(),
                    Strings.join( buildMethods, ", " )
                );
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    builderMapping.mirror,
                    Message.BUILDER_NO_BUILD_METHOD_FOUND,
                    buildMethodPattern,
                    builderType.getBuilder(),
                    builderType.getBuildingType(),
                    Strings.join( buildMethods, ", " )
                );
            }
        }

        return null;
    }

    private static BuilderPrism builderMappingPrism(Method method, MappingBuilderContext ctx) {
        BeanMapping beanMapping = method.getMappingOptions().getBeanMapping();
        if ( beanMapping != null && beanMapping.getBuilder() != null ) {
            return beanMapping.getBuilder();
        }
        return MapperConfiguration.getInstanceOn( ctx.getMapperTypeElement() ).getBuilderPrism();
    }
}
