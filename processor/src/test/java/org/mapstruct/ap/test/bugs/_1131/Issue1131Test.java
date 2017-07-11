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
package org.mapstruct.ap.test.bugs._1131;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1131")
@WithClasses({
    Issue1131Mapper.class,
    Issue1131MapperWithContext.class,
    Source.class,
    Target.class
})
public class Issue1131Test {

    @Test
    public void shouldUseCreateWithSourceNested() {

        Source source = new Source();
        source.setNested( new Source.Nested() );
        source.getNested().setProperty( "something" );
        source.setMoreNested( new ArrayList<Source.Nested>() );

        Target target = new Target();

        Issue1131Mapper.INSTANCE.merge( source, target );

        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested().getProperty() ).isEqualTo( "something" );
        assertThat( target.getNested().getInternal() ).isEqualTo( "from object factory" );
        assertThat( Issue1131Mapper.CALLED_METHODS ).containsExactly(
            "create(Source.Nested)",
            "create(List<Source.Nested>)"
        );
    }

    @Test
    public void shouldUseContextObjectFactory() {

        Source source = new Source();
        source.setNested( new Source.Nested() );
        source.getNested().setProperty( "something" );
        source.setMoreNested( new ArrayList<Source.Nested>() );

        Target target = new Target();

        Issue1131MapperWithContext.MappingContext context = new Issue1131MapperWithContext.MappingContext();
        Issue1131MapperWithContext.INSTANCE.merge( source, target, context );

        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested().getProperty() ).isEqualTo( "something" );
        assertThat( target.getNested().getInternal() ).isEqualTo( "from within @Context" );
        assertThat( context.getCalledMethods() ).containsExactly(
            "create(Source.Nested)",
            "create(List<Source.Nested>)"
        );
    }
}
