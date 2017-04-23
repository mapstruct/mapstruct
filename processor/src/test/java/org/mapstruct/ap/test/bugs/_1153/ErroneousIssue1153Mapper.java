/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.bugs._1153;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousIssue1153Mapper {

    @Mappings( {
        @Mapping( target = "readOnly", source = "nonNested"),
        @Mapping( target = "nestedTarget.readOnly", source = "nestedSource.nested"),
        @Mapping( target = "nestedTarget.writable", source = "nestedSource.writable"),
        @Mapping( target = "nestedTarget2.readOnly", ignore = true),
        @Mapping( target = "nestedTarget2.writable2", source = "nestedSource.writable"),
    } )
    Target map(Source source);

    class Source {

        public static class NestedSource {
            //CHECKSTYLE:OFF
            public String nested;
            public String writable;
            //CHECKSTYLE:ON
        }

        //CHECKSTYLE:OFF
        public String nonNested;
        public NestedSource nestedSource;
        public NestedSource nestedSource2;
        //CHECKSTYLE:ON
    }

    class Target {

        public static class NestedTarget {
            private String readOnly;
            private String writable;

            public String getReadOnly() {
                return readOnly;
            }

            public String getWritable() {
                return writable;
            }

            public void setWritable(String writable) {
                this.writable = writable;
            }
        }

        private String readOnly;
        private NestedTarget nestedTarget;
        private NestedTarget nestedTarget2;

        public String getReadOnly() {
            return readOnly;
        }

        public NestedTarget getNestedTarget() {
            return nestedTarget;
        }

        public void setNestedTarget(NestedTarget nestedTarget) {
            this.nestedTarget = nestedTarget;
        }

        public NestedTarget getNestedTarget2() {
            return nestedTarget2;
        }

        public void setNestedTarget2(NestedTarget nestedTarget2) {
            this.nestedTarget2 = nestedTarget2;
        }
    }
}
