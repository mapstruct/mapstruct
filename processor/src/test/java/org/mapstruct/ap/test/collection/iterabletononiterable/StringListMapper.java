/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletononiterable;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

public class StringListMapper {

    public String stringListToString(List<String> strings) {
        return strings == null ? null : Joiner.on( "-" ).join( strings );
    }

    public List<String> stringToStringList(String string) {
        return string == null ? null : Arrays.asList( string.split( "-" ) );
    }
}
