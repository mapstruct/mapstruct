/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import java.util.Optional;

public class OverloadedTarget {

    private Target.Nested date;

    public Optional<Target.Nested> getDate() {
        return Optional.ofNullable( date );
    }

    public void setDate(Target.Nested date) {
        this.date = date;
    }

    public void setDate(Optional<? extends Target.Nested> date) {
        this.date = date.orElse( null );
    }
}
