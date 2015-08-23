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
package org.mapstruct.ap.test.bugs._625;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Andreas Gudian
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    ObjectFactory.class,
    Source.class,
    Target.class,
    SourceTargetMapper.class
})
@IssueKey("625")
public class Issue625Test {
    @Test
    public void ignoresFactoryMethods() {
        Source s = new Source();
        s.setProp( "works" );

        Target target = new Target( true );
        Mappers.getMapper( SourceTargetMapper.class ).intoTarget( s, target );

        assertThat( target.getProp() ).isEqualTo( "works" );
    }
}
