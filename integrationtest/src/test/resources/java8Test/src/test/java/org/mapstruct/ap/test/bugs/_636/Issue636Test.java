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
package org.mapstruct.ap.test.bugs._636;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class Issue636Test {

    @Test
    public void shouldMapDataFromJava8Interface() {

        final long idFoo = 123;
        final String idBar = "Bar456";

        final Source source = new Source( idFoo, idBar );

        final Target target = SourceTargetMapper.INSTANCE.mapSourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().getId() ).isEqualTo( idFoo );
        assertThat( target.getBar() ).isNotNull();
        assertThat( target.getBar().getId() ).isEqualTo( idBar );
    }
}
