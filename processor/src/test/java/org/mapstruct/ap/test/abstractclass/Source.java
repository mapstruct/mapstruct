/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass;

import java.util.Calendar;

public class Source extends AbstractDto implements HasId, AlsoHasId {

    //CHECKSTYLE:OFF
    public final int publicSize;
    //CHECKSTYLE:ON
    private final int size;
    private final Calendar birthday;
    private final String notAttractingEqualsMethod = "no way";
    private final Holder<String> manuallyConverted = new Holder<String>( "What is the answer?" );

    public Source() {
        publicSize = 191;
        size = 181;
        birthday = Calendar.getInstance();
        birthday.set( 1948, 3, 26 );

        super.setId( 42L );
        this.publicId = 52L;
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
