package org.mapstruct.ap.test.source.interfaces;

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
