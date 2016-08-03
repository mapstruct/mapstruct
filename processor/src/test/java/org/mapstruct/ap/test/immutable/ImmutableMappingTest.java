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
package org.mapstruct.ap.test.immutable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.junit.Assert.assertEquals;

/** *
 * @author Ciaran Liedeman
 */
@IssueKey("73")
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ImmutableMappingTest {

    @Test
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED
    )
    public void constructorShouldBeUsed() {
        Source source = new Source();
        source.setA( "A" );
        source.setB( 1 );
        source.setC( (short) 2 );
        source.setD( 3.0 );


        Target target = SourceTargetMapper.INSTANCE.toTarget( source );

        assertEquals( source.getA(), target.getA() );
        assertEquals( source.getB(), target.getB() );
        assertEquals( source.getC(), target.getC() );
        assertEquals( source.getD(), target.getD(), 0 );
    }

}
