package org.mapstruct.ap.test.array.target;

public class ScientistDto {

    private String name;
    private String[] universities;
//    private String[] evaluations;

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

//    public String[] getEvaluations() {
//        return evaluations;
//    }
//
//    public void setEvaluations(String[] evaluations) {
//        this.evaluations = evaluations;
//    }
}
