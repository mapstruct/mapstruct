/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsource.exceptions;

/**
 *
 * @author Richard Lea <chigix@zoho.com>
 */
public class Bucket {

    String userId;

    public Bucket(String userId) {
        this.userId = userId;
    }

    public User getUser() throws NoSuchUser {
        throw new NoSuchUser();
    }

}
