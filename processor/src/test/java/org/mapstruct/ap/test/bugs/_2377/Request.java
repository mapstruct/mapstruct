/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2377;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Request {

    private Nullable<String> name = Nullable.undefined();
    private Nullable<List<ChildRequest>> children = Nullable.undefined();

    public Nullable<String> getName() {
        return name;
    }

    public void setName(Nullable<String> name) {
        this.name = name;
    }

    public Nullable<List<ChildRequest>> getChildren() {
        return children;
    }

    public void setChildren(Nullable<List<ChildRequest>> children) {
        this.children = children;
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
