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
package org.mapstruct.ap.test.bool;

public class Person {

    private Boolean married;
    private Boolean engaged;
    private YesNo divorced;
    private YesNo widowed;

    public Boolean isMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    public Boolean isEngaged() {
        return engaged != null && !engaged;
    }

    public Boolean getEngaged() {
        return engaged;
    }

    public void setEngaged(Boolean engaged) {
        this.engaged = engaged;
    }

    public YesNo getDivorced() {
        return divorced;
    }

    public void setDivorced(YesNo divorced) {
        this.divorced = divorced;
    }

    public YesNo getWidowed() {
        return widowed;
    }

    public void setWidowed(YesNo widowed) {
        this.widowed = widowed;
    }
}
