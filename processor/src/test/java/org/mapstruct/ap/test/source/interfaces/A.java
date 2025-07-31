/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.interfaces;

/**
 * @author Diego Pedregal
 */
public interface A {

    B b();

    C c();

    interface B {

        D d();

        interface D {

            String d();

        }

    }

    interface C {

        String c();

    }

}
