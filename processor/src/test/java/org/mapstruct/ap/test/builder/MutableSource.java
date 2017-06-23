package org.mapstruct.ap.test.builder;

public class MutableSource {
    private String name;
    private int age;
    private MutableParent mutableParent;

    public MutableParent getMutableParent() {
        return mutableParent;
    }

    public void setMutableParent(MutableParent mutableParent) {
        this.mutableParent = mutableParent;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
