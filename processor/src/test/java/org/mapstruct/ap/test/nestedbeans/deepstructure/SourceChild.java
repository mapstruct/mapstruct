package org.mapstruct.ap.test.nestedbeans.deepstructure;

class SourceChild {
    SourceSubChild autoMapChild;
    public SourceSubChild getAutoMapChild() {
        return autoMapChild;
    }

    public void setAutoMapChild(SourceSubChild autoMapChild) {
        this.autoMapChild = autoMapChild;
    }
}