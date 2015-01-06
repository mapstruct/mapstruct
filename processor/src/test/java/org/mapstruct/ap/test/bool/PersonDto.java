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

public class PersonDto {

    private String married;
    private String engaged;
    private String divorced;
    private Boolean widowed;

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getEngaged() {
        return engaged;
    }

    public void setEngaged(String engaged) {
        this.engaged = engaged;
    }

    public String getDivorced() {
        return divorced;
    }

    public void setDivorced(String divorced) {
        this.divorced = divorced;
    }

    public Boolean getWidowed() {
        return widowed;
    }

    public void setWidowed(Boolean widowed) {
        this.widowed = widowed;
    }

}
