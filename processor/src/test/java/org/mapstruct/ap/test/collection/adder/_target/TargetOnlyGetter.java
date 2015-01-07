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
package org.mapstruct.ap.test.collection.adder._target;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class TargetOnlyGetter {

    private List<Long> pets;

    public List<Long> getPets() {
        return pets;
    }

    public void addCat(Long cat) {
        // dummy method to test selection mechanims
    }

    public void addDog(Long cat) {
        // dummy method to test selection mechanims
    }

    public void addPets(Long cat) {
        // dummy method to test selection mechanims
    }

    public void addPet(Long pet) {
        AdderUsageObserver.setUsed( true );
        if ( pets == null ) {
            pets = new ArrayList<Long>();
        }
        pets.add( pet );
    }
}
