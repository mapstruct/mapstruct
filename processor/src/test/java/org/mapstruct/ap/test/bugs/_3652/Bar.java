/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3652;

public class Bar {

    private int secret;
    private int doesNotExistInFoo;

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public int getDoesNotExistInFoo() {
        return doesNotExistInFoo;
    }

    public void setDoesNotExistInFoo(int doesNotExistInFoo) {
        this.doesNotExistInFoo = doesNotExistInFoo;
    }

}
