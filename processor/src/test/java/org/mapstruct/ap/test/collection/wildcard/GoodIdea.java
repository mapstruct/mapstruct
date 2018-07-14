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
public class GoodIdea {

    private JAXBElement<? extends BigDecimal> content;
    private BigDecimal description;

    public JAXBElement<? extends BigDecimal> getContent() {
        return content;
    }

    public void setContent(JAXBElement<? extends BigDecimal> content) {
        this.content = content;
    }

    public BigDecimal getDescription() {
        return description;
    }

    public void setDescription(BigDecimal description) {
        this.description = description;
    }

}
