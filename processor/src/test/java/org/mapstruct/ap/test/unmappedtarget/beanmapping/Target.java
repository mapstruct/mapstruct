package org.mapstruct.ap.test.unmappedtarget.beanmapping;

public class Target {

    private String name;

    private String address;

    private Integer age;

    private NestedTarget nested;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public NestedTarget getNested() {
        return nested;
    }

    public void setNested(NestedTarget nested) {
        this.nested = nested;
    }

    public static class NestedTarget {

        private Double score;

        private Boolean flag;

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Boolean getFlag() {
            return flag;
        }

        public void setFlag(Boolean flag) {
            this.flag = flag;
        }
    }

}
