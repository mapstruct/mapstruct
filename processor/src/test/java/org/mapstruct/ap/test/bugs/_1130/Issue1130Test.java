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
package org.mapstruct.ap.test.bugs._1130;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.TargetType;
import org.mapstruct.ap.test.bugs._1130.Issue1130Mapper.ADto;
import org.mapstruct.ap.test.bugs._1130.Issue1130Mapper.AEntity;
import org.mapstruct.ap.test.bugs._1130.Issue1130Mapper.BEntity;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * Tests that when calling an update method for a previously null property, the factory method is called even if that
 * factory method has a {@link TargetType} annotation.
 *
 * @author Andreas Gudian
 */
@IssueKey("1130")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(Issue1130Mapper.class)
public class Issue1130Test {
    @Test
    public void factoryMethodWithTargetTypeInUpdateMethods() {
        AEntity aEntity = new AEntity();
        aEntity.setB( new BEntity() );

        ADto aDto = new ADto();
        Mappers.getMapper( Issue1130Mapper.class ).mergeA( aEntity, aDto );

        assertThat( aDto.getB() ).isNotNull();
        assertThat( aDto.getB().getPassedViaConstructor() ).isEqualTo( "created by factory" );
    }
}
