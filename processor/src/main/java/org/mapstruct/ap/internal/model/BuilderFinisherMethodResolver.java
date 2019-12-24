/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collection;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.prism.BuilderPrism;
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

        BuilderPrism builderMapping = method.getOptions().getBeanMapping().getBuilderPrism();
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

}
