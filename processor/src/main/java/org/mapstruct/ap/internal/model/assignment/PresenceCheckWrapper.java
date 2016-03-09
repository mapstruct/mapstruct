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
package org.mapstruct.ap.internal.model.assignment;

/**
 * Wraps the assignment in a target setter, if {@link org.mapstruct.Mapper#sourceValuePresenceCheckStrategy}
 * or {@link org.mapstruct.Mapping#sourceValuePresenceCheckStrategy} is set to
 * {@link org.mapstruct.SourceValuePresenceCheckStrategy#CUSTOM}.
 *
 * @author Sean Huang
 */
public class PresenceCheckWrapper extends AssignmentWrapper {

    private final String sourcegetPresenceChecker;

    public PresenceCheckWrapper( Assignment decoratedAssignment, String sourcegetPresenceChecker) {
        super( decoratedAssignment );
        this.sourcegetPresenceChecker = sourcegetPresenceChecker;
    }

    @Override
    public String getSourcePresenceCheckMethod() {
        return sourcegetPresenceChecker;
    }

}
