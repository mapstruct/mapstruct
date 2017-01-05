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
package org.mapstruct.ap.test.factories.c;

import org.mapstruct.ObjectFactory;
import org.mapstruct.ap.test.factories.Bar4;
import org.mapstruct.ap.test.factories.Foo4;

/**
 * @author Remo Meier
 */
public class BarFactory {

    @ObjectFactory
    public Bar4 createBar4(Foo4 foo4) {
        return new Bar4( foo4.getProp().toUpperCase() );
    }

}
