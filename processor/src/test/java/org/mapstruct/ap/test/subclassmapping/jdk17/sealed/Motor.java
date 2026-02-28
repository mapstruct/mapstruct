/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.jdk17.sealed;

public abstract sealed class Motor extends Vehicle {
    private int cc;

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public static final class Davidson extends Motor {
        private int numberOfExhausts;

        public int getNumberOfExhausts() {
            return numberOfExhausts;
        }

        public void setNumberOfExhausts(int numberOfExhausts) {
            this.numberOfExhausts = numberOfExhausts;
        }
    }

    public static final class Harley extends Motor {
        private int engineDb;

        public int getEngineDb() {
            return engineDb;
        }

        public void setEngineDb(int engineDb) {
            this.engineDb = engineDb;
        }
    }
}
