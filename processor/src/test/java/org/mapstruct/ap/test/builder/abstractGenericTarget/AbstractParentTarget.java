package org.mapstruct.ap.test.builder.abstractGenericTarget;

public interface AbstractParentTarget<T extends AbstractChildTarget> {
    int getCount();
    T getNested();
    AbstractChildTarget getNonGenericizedNested();
}
