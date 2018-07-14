/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._543;

import org.mapstruct.ap.test.bugs._543.dto.Source;
import org.mapstruct.ap.test.bugs._543.dto.Target;

/**
 * @author Filip Hrisafov
 */
public class SourceUtil {

    private SourceUtil() {

    }

    public static Target from(Source source) {
        if ( source == null ) {
            return null;
        }
        return new Target( source.getString() );
    }
}
