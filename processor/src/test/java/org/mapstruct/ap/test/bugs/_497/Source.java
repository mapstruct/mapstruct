package org.mapstruct.ap.test.bugs._497;

public class Source {
    private SourceType type;
    private String name;

    public Source(SourceType type, String name) {
        this.type = type;
        this.name = name;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
