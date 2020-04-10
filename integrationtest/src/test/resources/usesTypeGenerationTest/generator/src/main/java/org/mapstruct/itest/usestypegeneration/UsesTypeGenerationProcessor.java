/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.usestypegeneration;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Generate conversion uses.
 *
 * @author Filip Hrisafov
 *
 */
@SupportedAnnotationTypes("*")
public class UsesTypeGenerationProcessor extends AbstractProcessor {

    private boolean hasRun = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if ( !hasRun ) {
            try {
                    JavaFileObject dto = processingEnv.getFiler().createSourceFile( "org.mapstruct.itest.usestypegeneration.usage.StringUtils" );
                    Writer writer = dto.openWriter();

                    writer.append( "package org.mapstruct.itest.usestypegeneration.usage;" );
                    writer.append( "\n" );
                    writer.append( "public class StringUtils {" );
                    writer.append( "\n" );
                    writer.append( "    public static String upperCase(String string) {" );
                    writer.append( "\n" );
                    writer.append( "              return string == null ? null : string.toUpperCase();" );
                    writer.append( "\n" );
                    writer.append( "    }" );
                    writer.append( "\n" );
                    writer.append( "}" );

                    writer.flush();
                    writer.close();
            }
            catch (IOException e) {
                    throw new RuntimeException( e );
            }

            hasRun = true;
        }

        return false;
    }
}
