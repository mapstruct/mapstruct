/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedproperties.simple.source;

public class SourceRoot {

    private SourceProps props;

    public void setProps(SourceProps props) {
        this.props = props;
    }

    public SourceProps getProps() {
        return props;
    }

}
