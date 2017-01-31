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
package org.mapstruct.itest.targettypegeneration;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Generates a DTO.
 *
 * @author Gunnar Morling
 *
 */
@SupportedAnnotationTypes("*")
public class DtoGenerationProcessor extends AbstractProcessor {

    private boolean hasRun = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if ( !hasRun ) {
            try {
                    JavaFileObject dto = processingEnv.getFiler().createSourceFile( "org.mapstruct.itest.targettypegeneration.usage.OrderDto" );
                    Writer writer = dto.openWriter();

                    writer.append( "package org.mapstruct.itest.targettypegeneration.usage;" );
                    writer.append( "\n" );
                    writer.append( "public class OrderDto {" );
                    writer.append( "\n" );
                    writer.append( "    private String item;" );
                    writer.append( "\n" );
                    writer.append( "    public String getItem() {" );
                    writer.append( "              return item;" );
                    writer.append( "    }" );
                    writer.append( "\n" );
                    writer.append( "      public void setItem(String item) {" );
                    writer.append( "        this.item = item;" );
                    writer.append( "    }" );
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
