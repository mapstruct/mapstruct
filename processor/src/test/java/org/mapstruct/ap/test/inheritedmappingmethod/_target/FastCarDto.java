/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritedmappingmethod._target;

public class FastCarDto extends CarDto {
    private int coolnessFactor;

    public int getCoolnessFactor() {
        return coolnessFactor;
    }

    public void setCoolnessFactor(int coolnessFactor) {
        this.coolnessFactor = coolnessFactor;
    }

}
