/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2663;

/**
 * @author Filip Hrisafov
 */
public class Request {

    private Nullable<String> name = Nullable.undefined();
    private Nullable<ChildRequest> child = Nullable.undefined();

    public Nullable<String> getName() {
        return name;
    }

    public void setName(Nullable<String> name) {
        this.name = name;
    }

    public Nullable<ChildRequest> getChild() {
        return child;
    }

    public void setChild(Nullable<ChildRequest> child) {
        this.child = child;
    }

    public static class ChildRequest {

        private Nullable<String> name = Nullable.undefined();

        public Nullable<String> getName() {
            return name;
        }

        public void setName(Nullable<String> name) {
            this.name = name;
        }
    }
}
