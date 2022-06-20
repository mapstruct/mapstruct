/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.statics;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.references.statics.nonused.NonUsedMapper;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "410" )
@WithClasses( { Beer.class, BeerDto.class, Category.class } )
public class StaticsTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( {  BeerMapper.class, CustomMapper.class } )
    public void shouldUseStaticMethod() {

        Beer beer = new Beer(); // what the heck, open another one..
        beer.setPercentage( 7 );

        BeerDto result = BeerMapper.INSTANCE.mapBeer( beer );
        assertThat( result ).isNotNull();
        assertThat( result.getCategory() ).isEqualTo( Category.STRONG ); // why settle for less?
    }

    @ProcessorTest
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
