/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambigiousmapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousMapper {

    ErroneousMapper INSTANCE = Mappers.getMapper( ErroneousMapper.class );

    TrunkEntity map(TrunkDTO dto);

    default LeafEntity map1(LeafDTO dto) {
        return new LeafEntity();
    }

    // duplicated method, triggering ambigious mapping method
    default LeafEntity map2(LeafDTO dto) {
        return new LeafEntity();
    }

    // CHECKSTYLE:OFF
    class TrunkDTO {
        public BranchDTO branch;
    }

    class BranchDTO {
        public LeafDTO leafDTO;
    }

    class LeafDTO {
        public int numberOfVeigns;
    }

    class TrunkEntity {
        public BranchEntity branch;
    }

    class BranchEntity {
        public LeafEntity leafEntity;
    }

    class LeafEntity {
        public int numberOfVeigns;
    }
    // CHECKSTYLE ON
}
