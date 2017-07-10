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
package org.mapstruct.ap.test.bugs._1242;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Test mapper for properly resolving the best fitting factory method
 *
 * @author Andreas Gudian
 */
@Mapper(uses = TargetFactories.class)
public abstract class Issue1242Mapper {
    abstract TargetA toTargetA(SourceA source);

    abstract TargetB toTargetB(SourceB source);

    abstract void mergeA(SourceA source, @MappingTarget TargetA target);

    abstract void mergeB(SourceB source, @MappingTarget TargetB target);
}
