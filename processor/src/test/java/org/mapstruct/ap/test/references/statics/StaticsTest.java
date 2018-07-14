/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.statics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.references.statics.nonused.NonUsedMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
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
