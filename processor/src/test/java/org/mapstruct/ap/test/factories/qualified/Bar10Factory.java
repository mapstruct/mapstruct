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
package org.mapstruct.ap.test.factories.qualified;

import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;

/**
 * @author Remo Meier
 */
public class Bar10Factory {

    @ObjectFactory
    public Bar10 createBar10Lower(Foo10 foo10) {
        return new Bar10( foo10.getProp().toLowerCase() );
    }

    @TestQualifier
    @ObjectFactory
    public Bar10 createBar10Upper(Foo10 foo10) {
        return new Bar10( foo10.getProp().toUpperCase() );
    }

    @Named( "Bar10NamedQualifier" )
    @ObjectFactory
    public Bar10 createBar10Camel(Foo10 foo10) {
        char firstLetter =  Character.toUpperCase( foo10.getProp().charAt( 0 ) );
        return new Bar10( firstLetter + foo10.getProp().toLowerCase().substring( 1 ) );
    }

}
