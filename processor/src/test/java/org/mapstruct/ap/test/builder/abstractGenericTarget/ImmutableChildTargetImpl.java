package org.mapstruct.ap.test.builder.abstractGenericTarget;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder( builderClass = ImmutableChildTargetImpl.Builder.class)
public class ImmutableChildTargetImpl implements AbstractChildTarget  {
    private final String bar;

    private ImmutableChildTargetImpl(ImmutableChildTargetImpl.Builder builder) {
        this.bar = builder.bar;
    }

    public static ImmutableChildTargetImpl.Builder builder() {
        return new ImmutableChildTargetImpl.Builder();
    }

    public String getBar() {
        return bar;
    }

    public static class Builder {
        private String bar;

        public ImmutableChildTargetImpl.Builder bar(String bar) {
            this.bar = bar;
            return this;
        }

        public ImmutableChildTargetImpl build() {
            return new ImmutableChildTargetImpl( this );
        }


    }


}
