package org.mapstruct.ap.test.nestedbeans.deepstructure;

class TargetChild {
    private TargetSubChild autoMapChild;

    public TargetSubChild getAutoMapChild() {
        return autoMapChild;
    }

    public void setAutoMapChild(TargetSubChild autoMapChild) {
        this.autoMapChild = autoMapChild;
    }
}