/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2797.model;

/**
 * @author Ben Zegveld
 */
public class BasePerson {

    private Names names;

    public Names getNames() {
        return names;
    }

    public void setNames(Names names) {
        this.names = names;
    }

  public static class Names {

        private String first;
        private String last;

        public String getFirst() {
            return first;
        }

        public String getLast() {
            return last;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public void setLast(String last) {
            this.last = last;
        }
  }
}
