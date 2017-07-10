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

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * Contains non-conflicting factory methods for {@link TargetB}.
 *
 * @author Andreas Gudian
 */
public class TargetFactories {

    @ObjectFactory
    protected TargetB createTargetB(SourceB source, @TargetType Class<TargetB> clazz) {
        return new TargetB( "created by factory" );
    }

    protected TargetB createTargetB(@TargetType Class<TargetB> clazz) {
        throw new RuntimeException( "This method is not to be called" );
    }

    protected TargetB createTargetB() {
        throw new RuntimeException( "This method is not to be called" );
    }
}
