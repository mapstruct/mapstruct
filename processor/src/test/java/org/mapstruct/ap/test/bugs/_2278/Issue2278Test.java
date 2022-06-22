/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2278;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey("2278")
public class Issue2278Test {

    @ProcessorTest
    @WithClasses( Issue2278ReferenceMapper.class )
    public void testReferenceMergeBehaviour() {

        Issue2278ReferenceMapper.CarDTO dto = new Issue2278ReferenceMapper.CarDTO();
        dto.detailsDTO = new Issue2278ReferenceMapper.DetailsDTO();
        dto.detailsDTO.brand = "Ford";
        dto.detailsDTO.fuelType = "petrol";

        Issue2278ReferenceMapper.Car target = Issue2278ReferenceMapper.INSTANCE.map( dto );

        assertThat( target ).isNotNull();
        assertThat( target.details ).isNotNull();
        assertThat( target.details.brand ).isEqualTo( "Ford" );
        assertThat( target.details.model ).isNull();
        assertThat( target.details.type ).isEqualTo( "gto" );
        assertThat( target.details.fuel ).isEqualTo( "petrol" );

    }

    @ProcessorTest
    @WithClasses( Issue2278MapperA.class )
    public void shouldBehaveJustAsTestReferenceMergeBehaviour() {

        Issue2278MapperA.CarDTO dto = new Issue2278MapperA.CarDTO();
        dto.detailsDTO = new Issue2278MapperA.DetailsDTO();
        dto.detailsDTO.brand = "Ford";
        dto.detailsDTO.fuelType = "petrol";

        Issue2278MapperA.Car target = Issue2278MapperA.INSTANCE.map( dto );

        assertThat( target ).isNotNull();
        assertThat( target.details ).isNotNull();
        assertThat( target.details.brand ).isEqualTo( "Ford" );
        assertThat( target.details.model ).isNull();
        assertThat( target.details.type ).isEqualTo( "gto" );
        assertThat( target.details.fuel ).isEqualTo( "petrol" );

    }

    @ProcessorTest
    @WithClasses( Issue2278MapperB.class )
    public void shouldOverrideDetailsMappingWithRedefined() {

        Issue2278MapperB.Car source = new Issue2278MapperB.Car();
        source.details = new Issue2278MapperB.Details();
        source.details.type = "Ford";
        source.details.id1 = "id1";
        source.details.id2 = "id2";
        source.price = 20000f;

        Issue2278MapperB.CarDTO target1 = Issue2278MapperB.INSTANCE.map( source );

        assertThat( target1 ).isNotNull();
        assertThat( target1.amount ).isEqualTo( 20000f );
        assertThat( target1.detailsDTO ).isNotNull();
        assertThat( target1.detailsDTO.brand ).isEqualTo( "Ford" );
        assertThat( target1.detailsDTO.id1 ).isEqualTo( "id2" );
        assertThat( target1.detailsDTO.id2 ).isEqualTo( "id1" );

        // restore the mappings, just to make it logical again
        target1.detailsDTO.id1 = "id1";
        target1.detailsDTO.id2 = "id2";

        // now check the reverse inheritance
        Issue2278MapperB.Car target2 = Issue2278MapperB.INSTANCE.map2( target1 );

        assertThat( target2 ).isNotNull();
        assertThat( target2.price ).isEqualTo( 20000f );
        assertThat( target2.details ).isNotNull();
        assertThat( target2.details.type ).isEqualTo( "Ford" ); // should inherit
        assertThat( target2.details.id1 ).isEqualTo( "id1" ); // should be undone
        assertThat( target2.details.id2 ).isEqualTo( "id2" ); // should be undone
    }

}
