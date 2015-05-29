/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.source;

/**
 * Represents the mapping between one enum constant and another.
 *
 * @author Gunnar Morling
 */
public class EnumMapping {

    private final String source;
    private final String target;

    public EnumMapping(String source, String target) {
        this.source = source;
        this.target = target;
    }

    /**
     * @return the name of the constant in the source enum.
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the name of the constant in the target enum.
     */
    public String getTarget() {
        return target;
    }
}
