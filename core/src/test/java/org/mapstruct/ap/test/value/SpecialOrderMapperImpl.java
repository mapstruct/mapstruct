/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.value;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2016-02-25T21:16:18+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_05 (Oracle Corporation)"
)
// BEGIN: SpecialOrderMapperImpl
public class SpecialOrderMapperImpl implements SpecialOrderMapper {

    @Override
    public ExternalOrderType orderTypeToExternalOrderType(OrderType orderType) {
        if ( orderType == null ) {
            return ExternalOrderType.DEFAULT;
        }

        ExternalOrderType externalOrderType_;

        switch ( orderType ) {
            case STANDARD: externalOrderType_ = null;
            break;
            default: externalOrderType_ = ExternalOrderType.SPECIAL;
        }

        return externalOrderType_;
    }
}
// FINISH: SpecialOrderMapperImpl
