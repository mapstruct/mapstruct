package org.mapstruct.ap.test.injectionstrategy.spring.canonicalconstructor;

import org.springframework.stereotype.Component;

@Component
public class ContactRepository {

    public String getUserPhoneNumber(Integer userId) {
        return "+441134960000";
    }

}
