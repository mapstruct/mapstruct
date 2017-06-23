package org.mapstruct.ap.test.builder.abstractGenericTarget;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder(builderClass = ImmutableParentTargetImpl.Builder.class)
public class ImmutableParentTargetImpl implements AbstractParentTarget<ImmutableChildTargetImpl> {
    private final int count;
    private final ImmutableChildTargetImpl nested;
    private final AbstractChildTarget nonGenericizedNested;

    public ImmutableParentTargetImpl(Builder builder) {
        this.count = builder.count;
        this.nested = builder.nested;
        this.nonGenericizedNested = builder.nonGenericizedNested;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public AbstractChildTarget getNonGenericizedNested() {
        return nonGenericizedNested;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public ImmutableChildTargetImpl getNested() {
        return nested;
    }

    public static class Builder {
        private int count;
        private ImmutableChildTargetImpl nested;
        private AbstractChildTarget nonGenericizedNested;

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder nonGenericizedNested(AbstractChildTarget nonGenericizedNested) {
            this.nonGenericizedNested = nonGenericizedNested;
            return this;
        }

        public Builder nested(ImmutableChildTargetImpl nested) {
            this.nested = nested;
            return this;
        }

        public ImmutableParentTargetImpl build() {
            return new ImmutableParentTargetImpl( this );
        }

    }
}
