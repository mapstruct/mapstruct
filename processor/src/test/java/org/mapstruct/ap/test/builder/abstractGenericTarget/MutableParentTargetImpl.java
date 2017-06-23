package org.mapstruct.ap.test.builder.abstractGenericTarget;

public class MutableParentTargetImpl implements AbstractParentTarget<ImmutableChildTargetImpl> {
    private int count;
    private ImmutableChildTargetImpl nested;
    private AbstractChildTarget nonGenericizedNested;

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public ImmutableChildTargetImpl getNested() {
        return nested;
    }

    public void setNested(ImmutableChildTargetImpl nested) {
        this.nested = nested;
    }

    @Override
    public AbstractChildTarget getNonGenericizedNested() {
        return nonGenericizedNested;
    }

    public void setNonGenericizedNested(AbstractChildTarget nonGenericizedNested) {
        this.nonGenericizedNested = nonGenericizedNested;
    }
}
