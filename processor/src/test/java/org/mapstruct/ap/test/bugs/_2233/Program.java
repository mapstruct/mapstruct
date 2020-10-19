/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2233;

import java.util.Optional;

/**
 * @author Filip Hrisafov
 */
public class Program {

    private final String name;
    private final String number;

    public Program(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Optional<String> getName() {
        return Optional.ofNullable( name );
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable( number );
    }
}
