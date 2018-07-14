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
public class WaterQualityDto {

    private WaterQualityReportDto report;

    public WaterQualityReportDto getReport() {
        return report;
    }

    public void setReport(WaterQualityReportDto report) {
        this.report = report;
    }

}
