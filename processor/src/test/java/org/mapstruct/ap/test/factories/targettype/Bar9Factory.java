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
package org.mapstruct.ap.test.factories.targettype;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * @author Remo Meier
 */
public class Bar9Factory {

    @SuppressWarnings( "unchecked" )
    @ObjectFactory
    public <T extends Bar9Base> T createBar9(Foo9Base foo8, @TargetType Class<T> targetType) {
        if ( targetType == Bar9Base.class ) {
            return (T) new Bar9Base( foo8.getProp().toUpperCase() );
        }
        else {
            return (T) new Bar9Child( foo8.getProp().toUpperCase() );
        }
    }

}
