/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass;

import javax.xml.ws.Holder;

/**
 * @author Andreas Gudian
 */
public class ReferencedMapper extends AbstractReferencedMapper {
    @Override
    public int holderToInt(Holder<String> holder) {
        return 42;
    }
}
