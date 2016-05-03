/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.statefullmappers;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.mapstruct.Mapper;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public abstract class StatefullMapper {

    private static final Map<Integer, String> NY_CURICULUM_MAPPINGS = ImmutableMap.<Integer, String>builder()
        .put( 1, "English" )
        .put( 2, "Math" )
        .put( 3, "Science" )
        .build();

    private static final Map<Integer, String> OTHER_CURICULUM_MAPPINGS = ImmutableMap.<Integer, String>builder()
        .put( 1, "Math" )
        .put( 2, "Science" )
        .put( 3, "English" )
        .build();

    private final State state;

    public StatefullMapper( State state ) {
        this.state = state;
    }

    protected ClassNameEntity mapBasedOnState(ClassNumberDto dto) {
        ClassNameEntity entity = new ClassNameEntity();
        if ( "New York".equals( state.getName() ) ) {
            entity.setName( NY_CURICULUM_MAPPINGS.get( dto.getNumber() ) );
        }
        else {
            entity.setName( OTHER_CURICULUM_MAPPINGS.get( dto.getNumber() ) );

        }
        return entity;
    }

}
