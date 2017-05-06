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
package org.mapstruct.ap.test.overloading;

import java.util.Date;

public class Source {

    private Date updatedOn;
    private Date updatedOn2;

    public Source(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Source() {
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = new Date( updatedOn );
    }

    public Date getUpdatedOn2() {
        return updatedOn2;
    }

    public void setUpdatedOn2(Date updatedOn2) {
        this.updatedOn2 = updatedOn2;
    }

    public void setUpdatedOn2(long updatedOn) {
        this.updatedOn2 = new Date( updatedOn );
    }
}
