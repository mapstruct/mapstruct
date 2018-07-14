/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.mixed._target;

/**
 *
 * @author Sjaak Derksen
 */
public class WaterQualityWithDocumentDto {

    private WaterQualityReportDto document;

    public WaterQualityReportDto getDocument() {
        return document;
    }

    public void setDocument(WaterQualityReportDto document) {
        this.document = document;
    }

}
