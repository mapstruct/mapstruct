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
    date = "2016-02-25T21:16:17+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_05 (Oracle Corporation)"
)
// BEGIN: OrderMapperImpl
public class OrderMapperImpl implements OrderMapper {

    @Override
    public ExternalOrderType orderTypeToExternalOrderType(OrderType orderType) {
        if ( orderType == null ) {
            return null;
        }

        ExternalOrderType externalOrderType_;

        switch ( orderType ) {
            case EXTRA: externalOrderType_ = ExternalOrderType.SPECIAL;
            break;
            case STANDARD: externalOrderType_ = ExternalOrderType.DEFAULT;
            break;
            case NORMAL: externalOrderType_ = ExternalOrderType.DEFAULT;
            break;
            case RETAIL: externalOrderType_ = ExternalOrderType.RETAIL;
            break;
            case B2B: externalOrderType_ = ExternalOrderType.B2B;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderType );
        }

        return externalOrderType_;
    }

}
// FINISH: OrderMapperImpl
