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
package org.mapstruct.ap.test.inheritance.complex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SourceBaseMappingHelper {
    public Reference asReference(SourceBase source) {
        if ( null == source ) {
            return null;
        }

        Reference result = new Reference();
        result.setFoo( source.getFoo() );

        return result;
    }

    public Integer numberToInteger(Number number) {
        throw new RuntimeException( "This method should not have been called" );
    }

    public Integer integerToInteger(Integer number) {
        return number == null ? null : number + 1;
    }

    public List<Number> integerCollectionToNumberList(Collection<Integer> collection) {
        if ( collection == null ) {
            return null;
        }

        List<Number> list = new ArrayList<Number>( collection.size() );

        for ( Integer original : collection ) {
            list.add( Integer.valueOf( original + 1 ) );
        }

        return list;
    }
}
