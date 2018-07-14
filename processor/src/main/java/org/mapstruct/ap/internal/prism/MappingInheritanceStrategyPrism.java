/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.prism;


/**
 * Prism for the enum {@link org.mapstruct.MappingInheritanceStrategy}
 *
 * @author Andreas Gudian
 */
public enum MappingInheritanceStrategyPrism {

    EXPLICIT( false, false, false ),
    AUTO_INHERIT_FROM_CONFIG( true, true, false ),
    AUTO_INHERIT_REVERSE_FROM_CONFIG( true, false, true ),
    AUTO_INHERIT_ALL_FROM_CONFIG( true, true, true );

    private final boolean autoInherit;
    private final boolean applyForward;
    private final boolean applyReverse;

    MappingInheritanceStrategyPrism(boolean isAutoInherit, boolean applyForward, boolean applyReverse) {
        this.autoInherit = isAutoInherit;
        this.applyForward = applyForward;
        this.applyReverse = applyReverse;
    }

    public boolean isAutoInherit() {
        return autoInherit;
    }

    public boolean isApplyForward() {
        return applyForward;
    }

    public boolean isApplyReverse() {
        return applyReverse;
    }

}
