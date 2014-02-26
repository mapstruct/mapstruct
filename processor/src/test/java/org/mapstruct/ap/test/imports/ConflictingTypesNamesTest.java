/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.imports;

import org.mapstruct.ap.test.imports.from.Foo;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Test for generating a mapper which references types whose names clash with names of used annotations and exceptions.
 *
 * @author Gunnar Morling
 */
@IssueKey("112")
@WithClasses({
    Named.class,
    ParseException.class,
    SourceTargetMapper.class,
    List.class,
    Map.class,
    Foo.class,
    org.mapstruct.ap.test.imports.to.Foo.class
})
public class ConflictingTypesNamesTest extends MapperTestBase {

    @Test
    public void mapperImportingTypesWithConflictingNamesCanBeGenerated() {
        Named source = new Named();
        source.setFoo( 123 );
        source.setBar( 456L );

        ParseException target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();

        Foo foo = new Foo();
        foo.setName( "bar" );

        org.mapstruct.ap.test.imports.to.Foo foo2 = SourceTargetMapper.INSTANCE.fooToFoo( foo );
        assertThat( foo2 ).isNotNull();
        assertThat( foo2.getName() ).isEqualTo( "bar" );
    }
}
