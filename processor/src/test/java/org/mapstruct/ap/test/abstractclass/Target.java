/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass;

public class Target extends AbstractDto {

    //CHECKSTYLE:OFF
    public Long publicSize;
    //CHECKSTYLE:OFF

    private Long size;
    private String birthday;
    private boolean notAttractingEqualsMethod;
    private int manuallyConverted;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isNotAttractingEqualsMethod() {
        return notAttractingEqualsMethod;
    }

    public void setNotAttractingEqualsMethod(boolean notAttractingEqualsMethod) {
        this.notAttractingEqualsMethod = notAttractingEqualsMethod;
    }

    public int getManuallyConverted() {
        return manuallyConverted;
    }

    public void setManuallyConverted(int manuallyConverted) {
        this.manuallyConverted = manuallyConverted;
    }
}
