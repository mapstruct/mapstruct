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
package org.mapstruct.ap.spi;


import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

/**
 * The default JavaBeans-compliant implementation of the {@link AccessorNamingStrategy} service provider interface.
 *
 * @author Sjaak Derksen
 */
public class PresenceCheckAccessorNamingStrategy
    extends DefaultAccessorNamingStrategy
    implements AccessorNamingStrategy {

    @Override
    public MethodType getMethodType(ExecutableElement method) {
        if ( isPresenceCheckMethod( method ) ) {
            return MethodType.PRESENCE_CHECKER;
        }
        else  {
            return super.getMethodType( method );
        }
    }


    private boolean isPresenceCheckMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "has" ) && methodName.length() > 3 &&
            ( method.getReturnType().getKind() == TypeKind.BOOLEAN ||
                    "java.lang.Boolean".equals( getQualifiedName( method.getReturnType() ) ) );
    }

}
