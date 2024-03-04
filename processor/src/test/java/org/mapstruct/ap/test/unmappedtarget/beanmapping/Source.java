package org.mapstruct.ap.test.unmappedtarget.beanmapping;

public class Source {

    private String name;

    private Integer age;

    private NestedSource nested;

    public NestedSource getNested() {
        return nested;
    }

    public void setNested(NestedSource nested) {
        this.nested = nested;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static class NestedSource {
        private Double score;

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }

}
