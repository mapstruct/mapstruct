/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3839;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper( unmappedSourcePolicy = ReportingPolicy.ERROR )
public interface ErroneousIssue3839UnusedTargetThisMapper {

    @Mapping( target = ".", source = "record" )
    Target map(Source source);

    class Source {

        private Record record;

        public Record getRecord() {
            return record;
        }

        public void setRecord(Record record) {
            this.record = record;
        }
    }

    class Record {

        private String alpha;

        public String getAlpha() {
            return alpha;
        }

        public void setAlpha(String alpha) {
            this.alpha = alpha;
        }
    }

    class Target {

        private String zulu;

        public String getZulu() {
            return zulu;
        }

        public void setZulu(String zulu) {
            this.zulu = zulu;
        }
    }
}
