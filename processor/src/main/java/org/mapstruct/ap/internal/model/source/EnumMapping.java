/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

/**
 * Represents the mapping between one enum constant and another.
 *
 * @author Gunnar Morling
 */
public class EnumMapping {

    private final String source;
    private final String target;

    public EnumMapping(String source, String target) {
        this.source = source;
        this.target = target;
    }

    /**
     * @return the name of the constant in the source enum.
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the name of the constant in the target enum.
     */
    public String getTarget() {
        return target;
    }
}
