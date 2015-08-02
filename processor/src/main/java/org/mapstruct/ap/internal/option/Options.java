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
package org.mapstruct.ap.internal.option;


/**
 * The options passed to the code generator.
 *
 * @author Andreas Gudian
 * @author Gunnar Morling
 */
public class Options {
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final ReportingPolicy unmappedTargetPolicy;
    private final boolean alwaysGenerateSpi;
    private final String defaultComponentModel;

    public Options(boolean suppressGeneratorTimestamp, boolean suppressGeneratorVersionComment,
                   ReportingPolicy unmappedTargetPolicy,
                   String defaultComponentModel, boolean alwaysGenerateSpi) {
        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = suppressGeneratorVersionComment;
        this.unmappedTargetPolicy = unmappedTargetPolicy;
        this.defaultComponentModel = defaultComponentModel;
        this.alwaysGenerateSpi = alwaysGenerateSpi;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

    public ReportingPolicy getUnmappedTargetPolicy() {
        return unmappedTargetPolicy;
    }

    public String getDefaultComponentModel() {
        return defaultComponentModel;
    }

    public boolean isAlwaysGenerateSpi() {
        return alwaysGenerateSpi;
    }
}
