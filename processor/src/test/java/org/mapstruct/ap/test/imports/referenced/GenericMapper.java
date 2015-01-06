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
package org.mapstruct.ap.test.imports.referenced;

import org.mapstruct.TargetType;
import org.mapstruct.ap.test.imports.from.Foo;

/**
 * @author Andreas Gudian
 */
public class GenericMapper {

    @SuppressWarnings("unchecked")
    public <T> T fromFoo(Foo fromFoo, @TargetType Class<T> toFooClass) {
        if ( org.mapstruct.ap.test.imports.to.Foo.class == toFooClass ) {
            org.mapstruct.ap.test.imports.to.Foo result = new org.mapstruct.ap.test.imports.to.Foo();
            result.setName( fromFoo.getName() );

            return (T) result;
        }

        return null;
    }

    public NotImportedDatatype identity(NotImportedDatatype notImported) {
        return notImported;
    }
}
