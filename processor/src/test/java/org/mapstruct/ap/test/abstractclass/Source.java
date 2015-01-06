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
package org.mapstruct.ap.test.abstractclass;

import java.util.Calendar;

import javax.xml.ws.Holder;

public class Source extends AbstractDto implements HasId, AlsoHasId {

    private final int size;
    private final Calendar birthday;
    private final String notAttractingEqualsMethod = "no way";
    private final Holder<String> manuallyConverted = new Holder<String>( "What is the answer?" );

    public Source() {
        size = 181;
        birthday = Calendar.getInstance();
        birthday.set( 1948, 3, 26 );

        super.setId( 42L );
    }

    public int getSize() {
        return size;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public String getNotAttractingEqualsMethod() {
        return notAttractingEqualsMethod;
    }

    public Holder<String> getManuallyConverted() {
        return manuallyConverted;
    }
}
