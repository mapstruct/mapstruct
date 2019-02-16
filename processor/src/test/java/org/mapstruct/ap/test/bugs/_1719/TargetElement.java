/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1719;

import java.util.Objects;

public class TargetElement {

    private int id;
    private String name;
    private Target target;

    public TargetElement() {
    }

    public TargetElement(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Target getTarget() {
        return target;
    }

    /**
     * intentionally not a setter, to not further complicate this test case.
     *
     * @param target
     */
    public void updateTarget(Target target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        TargetElement that = (TargetElement) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }

}
