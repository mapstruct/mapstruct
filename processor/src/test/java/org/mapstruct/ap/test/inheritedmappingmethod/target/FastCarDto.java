package org.mapstruct.ap.test.inheritedmappingmethod.target;

public class FastCarDto extends CarDto {
    private int coolnessFactor;

    public int getCoolnessFactor() {
        return coolnessFactor;
    }

    public void setCoolnessFactor(int coolnessFactor) {
        this.coolnessFactor = coolnessFactor;
    }

}
