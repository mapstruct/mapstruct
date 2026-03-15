package org.mapstruct.ap.test.bugs._3949;

public interface ParentTargetInterface {
    ParentTarget getChild();

    void setChild(String child);

    void setChild(ParentTarget child);
}
