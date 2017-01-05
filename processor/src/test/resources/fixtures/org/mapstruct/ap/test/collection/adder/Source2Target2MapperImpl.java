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
package org.mapstruct.ap.test.collection.adder;

import javax.annotation.Generated;
import org.mapstruct.ap.test.collection.adder._target.Target2;
import org.mapstruct.ap.test.collection.adder.source.Foo;
import org.mapstruct.ap.test.collection.adder.source.Source2;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-12-30T19:10:39+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
public class Source2Target2MapperImpl extends Source2Target2Mapper {

    @Override
    public Target2 toTarget(Source2 source) {
        if ( source == null ) {
            return null;
        }

        Target2 target2 = new Target2();

        if ( source.getAttributes() != null ) {
            for ( Foo attribute : source.getAttributes() ) {
                target2.addAttribute( attribute );
            }
        }

        return target2;
    }
}
