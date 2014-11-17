package org.mapstruct.ap.test.array.source;

public class Scientist {

    private String name;
    private String[] universities;
    private int[] evaluations;

    public Scientist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getUniversities() {
        return universities;
    }

    public void setUniversities(String[] universities) {
        this.universities = universities;
    }

    public int[] getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(int[] evaluations) {
        this.evaluations = evaluations;
    }
}
