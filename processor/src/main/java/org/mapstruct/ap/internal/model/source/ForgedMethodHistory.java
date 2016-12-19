/**
 *  Copyright 2012-2016 Dmytro Polovinkin
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version q2.0 (the "License");
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
package org.mapstruct.ap.internal.model.source;

import org.mapstruct.ap.internal.model.common.Type;

public class ForgedMethodHistory {

    private final ForgedMethodHistory prevHistory;
    private final String sourceElement;
    private final String targetPropertyName;
    private final Type targetType;
    private final Type sourceType;

    public ForgedMethodHistory(ForgedMethodHistory history, String sourceElement, String targetPropertyName,
                               Type sourceType, Type targetType) {
        prevHistory = history;
        this.sourceElement = sourceElement;
        this.targetPropertyName = targetPropertyName;
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public String getSourceElement() {
        return sourceElement;
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public Type getTargetType() {
        return targetType;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public ForgedMethodHistory getPrevHistory() {
        return prevHistory;
    }

}
