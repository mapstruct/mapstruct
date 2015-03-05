package org.mapstruct.ap.test.bugs._new;

public class Target {
    public int multiplier;
    public String name;

    public Target(int multiplier, String name) {
        this.multiplier = multiplier;
        this.name = name;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
