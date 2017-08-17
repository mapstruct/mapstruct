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
package org.mapstruct.ap.internal.prism;


/**
 * Prism for the enum {@link org.mapstruct.MappingInheritanceStrategy}
 *
 * @author Andreas Gudian
 */
public enum MappingInheritanceStrategyPrism {

    EXPLICIT( false, false, false ),
    AUTO_INHERIT_FROM_CONFIG( true, true, false ),
    AUTO_INHERIT_REVERSE_FROM_CONFIG( true, false, true ),
    AUTO_INHERIT_ALL_FROM_CONFIG( true, true, true );

    private final boolean autoInherit;
    private final boolean applyForward;
    private final boolean applyReverse;

    MappingInheritanceStrategyPrism(boolean isAutoInherit, boolean applyForward, boolean applyReverse) {
        this.autoInherit = isAutoInherit;
        this.applyForward = applyForward;
        this.applyReverse = applyReverse;
    }

    public boolean isAutoInherit() {
        return autoInherit;
    }

    public boolean isApplyForward() {
        return applyForward;
    }

    public boolean isApplyReverse() {
        return applyReverse;
    }

}
