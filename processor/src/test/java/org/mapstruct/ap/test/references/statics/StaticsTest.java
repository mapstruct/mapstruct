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
package org.mapstruct.ap.test.references.statics;

import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.mapstruct.ap.test.references.statics.nonused.NonUsedMapper;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "410" )
@WithClasses( { Beer.class, BeerDto.class, Category.class } )
@RunWith(AnnotationProcessorTestRunner.class)
public class StaticsTest {

    private final GeneratedSource generatedSource = new GeneratedSource();

    @Rule
    public GeneratedSource getGeneratedSource() {
        return generatedSource;
    }

    @Test
    @WithClasses( {  BeerMapper.class, CustomMapper.class } )
    public void shouldUseStaticMethod() {

        Beer beer = new Beer(); // what the heck, open another one..
        beer.setPercentage( 7 );

        BeerDto result = BeerMapper.INSTANCE.mapBeer( beer );
        assertThat( result ).isNotNull();
        assertThat( result.getCategory() ).isEqualTo( Category.STRONG ); // why settle for less?
    }

    @Test
    @WithClasses( {  BeerMapperWithNonUsedMapper.class, NonUsedMapper.class } )
    public void shouldNotImportNonUsed() {

        Beer beer = new Beer(); // what the heck, open another one..
        beer.setPercentage( 7 );

        BeerDto result = BeerMapperWithNonUsedMapper.INSTANCE.mapBeer( beer );
        assertThat( result ).isNotNull();
        assertThat( result.getCategory() ).isEqualTo( Category.STRONG ); // I could shurly use one now..
        generatedSource.forMapper( BeerMapperWithNonUsedMapper.class ).containsNoImportFor( NonUsedMapper.class );


    }
}
