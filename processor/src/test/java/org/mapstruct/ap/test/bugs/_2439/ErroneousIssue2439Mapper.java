/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2439;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousIssue2439Mapper {

    @Mapping(target = "modeName", source = "mode.desc")
    LiveDto map(LiveEntity entity);

    class LiveEntity {
        private final LiveMode[] mode;

        public LiveEntity(LiveMode... mode) {
            this.mode = mode;
        }

        public LiveMode[] getMode() {
            return mode;
        }
    }

    class LiveDto {
        private String modeName;

        public String getModeName() {
            return modeName;
        }

        public void setModeName(String modeName) {
            this.modeName = modeName;
        }
    }

    enum LiveMode {
        TEST,
        PROD,
    }
}
