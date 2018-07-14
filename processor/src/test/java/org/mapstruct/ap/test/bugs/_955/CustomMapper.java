/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._955;

import java.util.Map;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.bugs._955.dto.Person;

/**
 *
 * @author Sjaak Derksen
 */
public class CustomMapper {

    public Map<String, Person> merge(Map<String, Person> source, @MappingTarget Map<String, Person> destination) {
        return destination;
    }
}
