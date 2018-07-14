/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._913;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class Helper {

    public List<String> toList(String in) {
        return Arrays.asList( in.split( "," ) );
    }
}
