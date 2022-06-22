/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.mapstruct.ap.test.nestedbeans.maps.AntonymsDictionary;
import org.mapstruct.ap.test.nestedbeans.maps.AntonymsDictionaryDto;
import org.mapstruct.ap.test.nestedbeans.maps.AutoMapMapper;
import org.mapstruct.ap.test.nestedbeans.maps.Word;
import org.mapstruct.ap.test.nestedbeans.maps.WordDto;
import org.mapstruct.ap.test.nestedbeans.multiplecollections.Garage;
import org.mapstruct.ap.test.nestedbeans.multiplecollections.GarageDto;
import org.mapstruct.ap.test.nestedbeans.multiplecollections.MultipleListMapper;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * This test is for a case when several identical methods could be generated, what is an easy edge case to miss.
 */
public class MultipleForgedMethodsTest {

    @WithClasses({
        Word.class, WordDto.class, AntonymsDictionaryDto.class, AntonymsDictionary.class, AutoMapMapper.class
    })
    @ProcessorTest
    public void testNestedMapsAutoMap() {

        HashMap<WordDto, WordDto> dtoAntonyms = new HashMap<>();
        HashMap<Word, Word> entityAntonyms = new HashMap<>();

        String[] words = { "black", "good", "up", "left", "fast" };
        String[] antonyms = { "white", "bad", "down", "right", "slow" };

        assert words.length == antonyms.length : "Words length and antonyms length differ, please fix test data";

        for ( int i = 0; i < words.length; i++ ) {
            String word = words[i];
            String antonym = antonyms[i];
            dtoAntonyms.put( new WordDto( word ), new WordDto( antonym ) );
            entityAntonyms.put( new Word( word ), new Word( antonym ) );
        }

        AntonymsDictionary mappedAntonymsDictionary = AutoMapMapper.INSTANCE.entityToDto(
            new AntonymsDictionaryDto( dtoAntonyms ) );

        assertEquals(
            new AntonymsDictionary( entityAntonyms ),
            mappedAntonymsDictionary,
            "Mapper did not map dto to entity correctly"
        );
    }

    @WithClasses({
        MultipleListMapper.class, Garage.class, GarageDto.class, Car.class, CarDto.class,
        Wheel.class, WheelDto.class
    })
    @ProcessorTest
    public void testMultipleCollections() {
        GarageDto dto = new GarageDto(
            Arrays.asList( new CarDto(
                "New car",
                2017,
                Arrays.asList( new WheelDto( true, false ), new WheelDto( true, true ) )
            ) ),
            Arrays.asList(
                new CarDto(
                    "Old car-1",
                    1978,
                    Arrays.asList( new WheelDto( false, false ), new WheelDto( false, true ) )
                ),
                new CarDto(
                    "Old car-2",
                    1934,
                    Arrays.asList( new WheelDto( false, true ), new WheelDto( false, false ) )
                )
            )
        );

        Garage entity = new Garage(
            Arrays.asList( new Car(
                "New car",
                2017,
                Arrays.asList( new Wheel( true, false ), new Wheel( true, true ) )
            ) ),
            Arrays.asList(
                new Car( "Old car-1", 1978, Arrays.asList( new Wheel( false, false ), new Wheel( false, true ) ) ),
                new Car( "Old car-2", 1934, Arrays.asList( new Wheel( false, true ), new Wheel( false, false ) ) )
            )
        );

        GarageDto mappedDto = MultipleListMapper.INSTANCE.convert( entity );

        assertEquals( dto, mappedDto, "Mapper did not map entity to dto correctly" );

    }

}
