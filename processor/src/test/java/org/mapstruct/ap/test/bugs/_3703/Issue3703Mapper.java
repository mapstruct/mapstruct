/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3703;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.bugs._3703.dto.Contact;

@Mapper
public interface Issue3703Mapper {

    Contact map(org.mapstruct.ap.test.bugs._3703.entity.Contact contact);

    org.mapstruct.ap.test.bugs._3703.entity.Contact map(Contact contact);

    @AfterMapping
    default void afterMapping(@MappingTarget Contact target, org.mapstruct.ap.test.bugs._3703.entity.Contact contact) {
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Contact.Builder targetBuilder,
                              org.mapstruct.ap.test.bugs._3703.entity.Contact contact) {
    }

    @AfterMapping
    default void afterMapping(@MappingTarget org.mapstruct.ap.test.bugs._3703.entity.Contact target, Contact contact) {
    }

    @AfterMapping
    default void afterMapping(@MappingTarget org.mapstruct.ap.test.bugs._3703.entity.Contact.Builder targetBuilder,
                              Contact contact) {
    }
}
