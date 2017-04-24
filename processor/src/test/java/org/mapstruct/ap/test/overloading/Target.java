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

public class Target {

    private long updatedOnTarget;
    private long updatedOnTarget2;

    public long getUpdatedOnTarget() {
        return updatedOnTarget;
    }

    public void setUpdatedOnTarget(long updatedOnTarget) {
        this.updatedOnTarget = updatedOnTarget;
    }

    public void setUpdatedOnTarget( Date updatedOn ) {
        if (updatedOn == null) {
            return;
        }
        this.updatedOnTarget = updatedOn.getTime();
    }

    public long getUpdatedOnTarget2() {
        return updatedOnTarget2;
    }

    public void setUpdatedOnTarget2(long updatedOnTarget2) {
        this.updatedOnTarget2 = updatedOnTarget2;
    }

    public void setUpdatedOnTarget2( Date updatedOn ) {
        if (updatedOn == null) {
            return;
        }
        this.updatedOnTarget2 = updatedOn.getTime();
    }
}
