/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.compilation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An expected {@link javax.tools.Diagnostic.Kind#NOTE}.
 *
 * @author Sjaak Derksen
 */

@Repeatable(ExpectedNote.ExpectedNotes.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExpectedNote {

    String value();

    /**
     * The notes in the order they are expected
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExpectedNotes {

        /**
         *  Regexp voor the value.
         *
         * @return
         */
        ExpectedNote[] value();
    }
}
