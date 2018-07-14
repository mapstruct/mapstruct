/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.wildcard;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;

/**
 *
 * @author Sjaak Derksen
 */
public class CunningPlan {
    private BigDecimal content;
    private JAXBElement<? super BigDecimal> description;

    public BigDecimal getContent() {
        return content;
    }

    public void setContent(BigDecimal content) {
        this.content = content;
    }

    public JAXBElement<? super BigDecimal> getDescription() {
        return description;
    }

    public void setDescription(JAXBElement<? super BigDecimal> description) {
        this.description = description;
    }
}
