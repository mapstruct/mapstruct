/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.collection.adder.target;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class TargetHuman {

    private List<Integer> teeth;

    public List<Integer> getTeeth() {
        return teeth;
    }

    public void setTeeth(List<Integer> teeth) {
        this.teeth = teeth;
    }

    public void addTooth(Integer pet) {
        AdderUsageObserver.setUsed( true );
        if ( teeth == null ) {
            teeth = new ArrayList<Integer>();
        }
        teeth.add( pet );
    }

    public void addTeeth(Integer tooth) {
        if ( teeth == null ) {
            teeth = new ArrayList<Integer>();
        }
        teeth.add( tooth );
    }
}
