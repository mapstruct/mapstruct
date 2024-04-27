package org.mapstruct.ap.test.nestedbeans.deepstructure;

class TargetChild {
    TargetSubChild autoMapChild;

    public TargetSubChild getAutoMapChild() {
        return autoMapChild;
    }

    public void setAutoMapChild(TargetSubChild autoMapChild) {
        this.autoMapChild = autoMapChild;
    }
}