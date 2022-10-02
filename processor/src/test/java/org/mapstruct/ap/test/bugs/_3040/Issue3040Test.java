/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3040;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author orange add
 */
@WithClasses( Issue3040Mapper.class )
public class Issue3040Test {

    @ProcessorTest
    public void shouldOnlyMappingControlCompileSuccess() {
        Issue3040Mapper mapper = Mappers.getMapper( Issue3040Mapper.class );
        Issue3040Mapper.Fruit fruit = new Issue3040Mapper.Fruit();
        fruit.setName( "apple" );
        Issue3040Mapper.FruitDto fruitDto = mapper.map( fruit );
        assertThat( fruitDto.getName() ).isEqualTo( fruit.getName() );
    }
}
