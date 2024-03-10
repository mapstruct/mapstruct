/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.canonicalconstructor;

import org.springframework.stereotype.Component;

@Component
public class ContactRepository {

    public String getUserPhoneNumber(Integer userId) {
        return "+441134960000";
    }

}
