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
package org.mapstruct.ap.test.bugs._1130;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * Test mapper similar to the one provided in the issue.
 *
 * @author Andreas Gudian
 */
@Mapper
public abstract class Issue1130Mapper {
    static class AEntity {
        private BEntity b;

        public BEntity getB() {
            return b;
        }

        public void setB(BEntity b) {
            this.b = b;
        }
    }

    static class BEntity {
    }

    static class ADto {
        private BDto b;

        public BDto getB() {
            return b;
        }

        public void setB(BDto b) {
            this.b = b;
        }
    }

    class BDto {
        private final String passedViaConstructor;

        BDto(String passedViaConstructor) {
            this.passedViaConstructor = passedViaConstructor;
        }

        String getPassedViaConstructor() {
            return passedViaConstructor;
        }
    }

    abstract void mergeA(AEntity source, @MappingTarget ADto target);

    abstract void mergeB(BEntity source, @MappingTarget BDto target);

    @ObjectFactory
    protected BDto createB(@TargetType Class<BDto> clazz) {
        return new BDto( "created by factory" );
    }
}
