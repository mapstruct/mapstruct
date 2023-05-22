/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.context;

public class Source {
    private final String name;
    private final String address;

    // CHECKSTYLE:OFF
    @Confidential("management")
    public final String salary;
    // CHECKSTYLE:ON

    public Source(String name, String address, String salary) {
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    @Confidential("company")
    public String getAddress() {
        return address;
    }
}
