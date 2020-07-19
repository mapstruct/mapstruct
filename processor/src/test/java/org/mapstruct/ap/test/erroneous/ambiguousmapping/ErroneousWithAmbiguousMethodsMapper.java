/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousmapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousWithAmbiguousMethodsMapper {

    ErroneousWithAmbiguousMethodsMapper INSTANCE = Mappers.getMapper( ErroneousWithAmbiguousMethodsMapper.class );

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
        public LeafDTO leaf;
    }

    class LeafDTO {
        public int numberOfVeigns;
    }

    class TrunkEntity {
        public BranchEntity branch;
    }

    class BranchEntity {
        public LeafEntity leaf;
    }

    class LeafEntity {
        public int numberOfVeigns;
    }
    // CHECKSTYLE ON
}
