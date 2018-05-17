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
package org.mapstruct.ap.test.references.usesPlain;

import org.mapstruct.ap.test.references.Bar;

/**
 * @author Christian Bandowski
 */
public class Target {
    private boolean createdFromObjectFactory;
    private Bar value;

    public Target() {
    }

    public Target(boolean createdFromObjectFactory) {
        this.createdFromObjectFactory = createdFromObjectFactory;
    }

    public Bar getValue() {
        return value;
    }

    public void setValue(Bar value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Target target = (Target) o;

        if ( createdFromObjectFactory != target.createdFromObjectFactory ) {
            return false;
        }
        return value != null ? value.equals( target.value ) : target.value == null;
    }

    @Override
    public int hashCode() {
        int result = ( createdFromObjectFactory ? 1 : 0 );
        result = 31 * result + ( value != null ? value.hashCode() : 0 );
        return result;
    }
}
