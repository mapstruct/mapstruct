package org.mapstruct.ap.test.builder.nestedprop;

public class ImmutableTargetContainer {
    private final String foo;

    ImmutableTargetContainer(ImmutableTargetContainer.Builder builder) {
        this.foo = builder.foo;
    }

    public static ImmutableTargetContainer.Builder builder() {
        return new ImmutableTargetContainer.Builder();
    }


    public String getFoo() {
        return foo;
    }

    public static class Builder {
        private String foo;

        public ImmutableTargetContainer build() {
            return new ImmutableTargetContainer( this );
        }

        public ImmutableTargetContainer.Builder foo(String foo) {
            this.foo = foo;
            return this;
        }
    }
}
