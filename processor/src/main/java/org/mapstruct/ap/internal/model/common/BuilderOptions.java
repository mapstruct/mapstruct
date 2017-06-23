package org.mapstruct.ap.internal.model.common;

public class BuilderOptions {
    private final Type builderClass;
    private final String buildMethod;
    private final String staticBuilderMethod;

    public BuilderOptions(Type builderClass, String buildMethod, String staticBuilderMethod) {
        this.builderClass = builderClass;
        this.buildMethod = buildMethod;
        this.staticBuilderMethod = staticBuilderMethod;
    }

    public String getBuildMethod() {
        return buildMethod;
    }

    public Type getBuilderClass() {
        return builderClass;
    }

    public String getStaticBuilderMethod() {
        return staticBuilderMethod;
    }
}
