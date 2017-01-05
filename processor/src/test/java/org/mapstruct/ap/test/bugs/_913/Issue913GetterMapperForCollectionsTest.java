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
package org.mapstruct.ap.test.bugs._913;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * All these test cases test the possible combinations in the GetterMapperForCollections.
 *
 * The target object is assumed to have getter and setter access.
 *
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    DomainWithoutSetter.class,
    Dto.class,
    DtoWithPresenceCheck.class,
    DomainWithoutSetterDtoWithNvmsNullMapper.class,
    DomainWithoutSetterDtoWithNvmsDefaultMapper.class,
    DomainWithoutSetterDtoWithPresenceCheckMapper.class,
    Helper.class})
@IssueKey( "913" )
public class Issue913GetterMapperForCollectionsTest {

     /**
     * The null value mapping strategy on type level (Mapper) should generate forged methods for the
     * conversion from string to long that return null in the entire mapper, so also for the forged
     * mapper. Note the default NVMS is RETURN_NULL.
     */
    @Test
    public void shouldReturnNullForNvmsReturnNullForCreate() {

        Dto dto = new Dto();
        DomainWithoutSetter domain = DomainWithoutSetterDtoWithNvmsNullMapper.INSTANCE.create( dto );

        doControlAsserts( domain );
        assertThat( domain.getStrings() ).isEmpty();
        assertThat( domain.getLongs() ).isEmpty();
    }

    /**
     * The null value mapping strategy on type level (Mapper) should generate forged methods for the
     * conversion from string to long that return null in the entire mapper, so also for the forged
     * mapper. Note the default NVMS is RETURN_NULL.
     */
    @Test
    public void shouldReturnNullForNvmsReturnNullForUpdate() {

        Dto dto = new Dto();
        DomainWithoutSetter domain = new DomainWithoutSetter();
        DomainWithoutSetterDtoWithNvmsNullMapper.INSTANCE.update( dto, domain );

        doControlAsserts( domain );
        assertThat( domain.getStrings() ).isEmpty();
        assertThat( domain.getLongs() ).isEmpty();

    }

    /**
     * The null value mapping strategy on type level (Mapper) should generate forged methods for the
     * conversion from string to long that return null in the entire mapper, so also for the forged
     * mapper. Note the default NVMS is RETURN_NULL.
     */
    @Test
    public void shouldReturnNullForNvmsReturnNullForUpdateWithReturn() {

        Dto dto = new Dto();
        DomainWithoutSetter domain1 = new DomainWithoutSetter();
        DomainWithoutSetter domain2 =
            DomainWithoutSetterDtoWithNvmsNullMapper.INSTANCE.updateWithReturn( dto, domain1 );

        doControlAsserts( domain1, domain2 );
        assertThat( domain1.getStrings() ).isEmpty();
        assertThat( domain1.getLongs() ).isEmpty();
        assertThat( domain2.getStrings() ).isEmpty();
        assertThat( domain2.getLongs() ).isEmpty();
    }

   /**
     * The null value mapping strategy on type level (Mapper) should generate forged methods for the
     * conversion from string to long that return default in the entire mapper, so also for the forged
     * mapper. Note the default NVMS is RETURN_NULL.
     *
     * However, for plain mappings (strings to strings) the result will be null.
     */
    @Test
    public void shouldReturnDefaultForNvmsReturnDefaultForCreate() {

        Dto dto = new Dto();
        DomainWithoutSetter domain = DomainWithoutSetterDtoWithNvmsDefaultMapper.INSTANCE.create( dto );

        doControlAsserts( domain );
        assertThat( domain.getStrings() ).isEmpty();
        assertThat( domain.getLongs() ).isEmpty();
    }

    /**
     * The null value mapping strategy on type level (Mapper) should generate forged methods for the conversion from
     * string to long that return default in the entire mapper, so also for the forged mapper. Note the default NVMS is
     * RETURN_NULL.
     *
     * However, for plain mappings (strings to strings) the result will be null.
     */
    @Test
    public void shouldReturnDefaultForNvmsReturnDefaultForUpdate() {

        Dto dto = new Dto();
        DomainWithoutSetter domain = new DomainWithoutSetter();
        DomainWithoutSetterDtoWithNvmsDefaultMapper.INSTANCE.update( dto, domain );

        doControlAsserts( domain );
        assertThat( domain.getStrings() ).isEmpty();
        assertThat( domain.getLongs() ).isEmpty();
    }

    /**
     * The null value mapping strategy on type level (Mapper) should generate forged methods for the
     * conversion from string to long that return default in the entire mapper, so also for the forged
     * mapper. Note the default NVMS is
     * RETURN_NULL.
     *
     * However, for plain mappings (strings to strings) the result will be null.
     *
     */
    @Test
    public void shouldReturnDefaultForNvmsReturnDefaultForUpdateWithReturn() {

        Dto dto = new Dto();
        DomainWithoutSetter domain1 = new DomainWithoutSetter();
        DomainWithoutSetter domain2 =
            DomainWithoutSetterDtoWithNvmsDefaultMapper.INSTANCE.updateWithReturn( dto, domain1 );

        doControlAsserts( domain1, domain2 );
        assertThat( domain1.getLongs() ).isEqualTo( domain2.getLongs() );
        assertThat( domain1.getStrings() ).isEmpty();
        assertThat( domain1.getLongs() ).isEmpty();
        assertThat( domain2.getStrings() ).isEmpty();
        assertThat( domain2.getLongs() ).isEmpty();
    }

    /**
     * Test create method ICW presence checker
     *
     */
    @Test
    public void shouldReturnNullForCreateWithPresenceChecker() {

        DtoWithPresenceCheck dto = new DtoWithPresenceCheck();
        DomainWithoutSetter domain = DomainWithoutSetterDtoWithPresenceCheckMapper.INSTANCE.create( dto );

        doControlAsserts( domain );
        assertThat( domain.getStrings() ).isEmpty();
        assertThat( domain.getLongs() ).isEmpty();
    }

    /**
     * Test update method ICW presence checker
     *
     */
    @Test
    public void shouldReturnNullForUpdateWithPresenceChecker() {

        DtoWithPresenceCheck dto = new DtoWithPresenceCheck();
        DomainWithoutSetter domain = new DomainWithoutSetter();
        DomainWithoutSetterDtoWithPresenceCheckMapper.INSTANCE.update( dto, domain );

        doControlAsserts( domain );
        assertThat( domain.getStrings() ).isEmpty();
        assertThat( domain.getLongs() ).isEmpty();
    }

    /**
     * Test update with return method ICW presence checker
     *
     */
    @Test
    public void shouldReturnNullForUpdateWithReturnWithPresenceChecker() {

        DtoWithPresenceCheck dto = new DtoWithPresenceCheck();
        DomainWithoutSetter domain1 = new DomainWithoutSetter();
        DomainWithoutSetter domain2 =
            DomainWithoutSetterDtoWithPresenceCheckMapper.INSTANCE.updateWithReturn( dto, domain1 );

        doControlAsserts( domain1, domain2 );
        assertThat( domain1.getLongs() ).isEqualTo( domain2.getLongs() );
        assertThat( domain1.getStrings() ).isEmpty();
        assertThat( domain1.getLongs() ).isEmpty();
        assertThat( domain2.getStrings() ).isEmpty();
        assertThat( domain2.getLongs() ).isEmpty();
    }



    /**
     * These assert check if non-null and default mapping is working as expected.
     *
     * @param domain
     */
    private void doControlAsserts( DomainWithoutSetter domain ) {
        assertThat( domain.getStringsInitialized() ).containsOnly( "5" );
        assertThat( domain.getLongsInitialized() ).containsOnly( 5L );
        assertThat( domain.getStringsWithDefault() ).containsOnly( "3" );
    }

    /**
     * These assert check if non-null and default mapping is working as expected.
     *
     * @param domain
     */
    private void doControlAsserts( DomainWithoutSetter domain1, DomainWithoutSetter domain2) {
        assertThat( domain1 ).isEqualTo( domain2 );
        assertThat( domain1.getStringsInitialized() ).containsOnly( "5" );
        assertThat( domain1.getLongsInitialized() ).containsOnly( 5L );
        assertThat( domain1.getStringsWithDefault() ).containsOnly( "3" );
        assertThat( domain2.getStringsInitialized() ).containsOnly( "5" );
        assertThat( domain2.getLongsInitialized() ).containsOnly( 5L );
        assertThat( domain2.getStringsWithDefault() ).containsOnly( "3" );
        assertThat( domain1.getStringsInitialized() ).isEqualTo( domain2.getStringsInitialized() );
        assertThat( domain1.getLongsInitialized() ).isEqualTo( domain2.getLongsInitialized() );
        assertThat( domain1.getStringsWithDefault() ).isEqualTo( domain2.getStringsWithDefault() );
    }

}
