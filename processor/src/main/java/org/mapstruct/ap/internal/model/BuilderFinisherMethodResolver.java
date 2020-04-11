/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collection;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.util.Extractor;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * @author Filip Hrisafov
 */
public class BuilderFinisherMethodResolver {

    private static final String DEFAULT_BUILD_METHOD_NAME = "build";

    private static final Extractor<ExecutableElement, String> EXECUTABLE_ELEMENT_NAME_EXTRACTOR =
        executableElement -> {
            StringBuilder sb = new StringBuilder( executableElement.getSimpleName() );

            sb.append( '(' );
            for ( VariableElement parameter : executableElement.getParameters() ) {
                sb.append( parameter );
            }

            sb.append( ')' );
            return sb.toString();
        };

    private BuilderFinisherMethodResolver() {
    }

    public static MethodReference getBuilderFinisherMethod(Method method, BuilderType builderType,
        MappingBuilderContext ctx) {
        Collection<ExecutableElement> buildMethods = builderType.getBuildMethods();
        if ( buildMethods.isEmpty() ) {
            //If we reach this method this should never happen
            return null;
        }

        BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();
        if ( builder == null && buildMethods.size() == 1 ) {
            return MethodReference.forMethodCall( first( buildMethods ).getSimpleName().toString() );
        }
        else {
            String buildMethodPattern = DEFAULT_BUILD_METHOD_NAME;
            if ( builder != null ) {
                buildMethodPattern = builder.buildMethod().get();
            }
            for ( ExecutableElement buildMethod : buildMethods ) {
                String methodName = buildMethod.getSimpleName().toString();
                if ( methodName.matches( buildMethodPattern ) ) {
                    return MethodReference.forMethodCall( methodName );
                }
            }

            if ( builder == null ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.BUILDER_NO_BUILD_METHOD_FOUND_DEFAULT,
                    buildMethodPattern,
                    builderType.getBuilder(),
                    builderType.getBuildingType(),
                    Strings.join( buildMethods, ", ", EXECUTABLE_ELEMENT_NAME_EXTRACTOR )
                );
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    builder.mirror(),
                    Message.BUILDER_NO_BUILD_METHOD_FOUND,
                    buildMethodPattern,
                    builderType.getBuilder(),
                    builderType.getBuildingType(),
                    Strings.join( buildMethods, ", ", EXECUTABLE_ELEMENT_NAME_EXTRACTOR )
                );
            }
        }

        return null;
    }

}
