package org.mapstruct.ap.test.builder.parentchild;

import org.mapstruct.MappedByBuilder;

@MappedByBuilder(builderClass = ImmutableChild.Builder.class)
public class ImmutableChild {

    private final String bar;

    private ImmutableChild(Builder builder) {
        this.bar = builder.bar;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getBar() {
        return bar;
    }

    public static class Builder {
        private String bar;

        public ImmutableChild.Builder bar(String bar) {
            this.bar = bar;
            return this;
        }

        public ImmutableChild build() {
            return new ImmutableChild( this );
        }


    }
}
