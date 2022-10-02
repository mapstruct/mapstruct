/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface MostSpecificResultTypeSelectingUpdateMapper {

    MostSpecificResultTypeSelectingUpdateMapper INSTANCE = Mappers.getMapper(
        MostSpecificResultTypeSelectingUpdateMapper.class );

    @Mapping(target = "apple", source = "fruit")
    @Mapping(target = "goldenApple", source = "fruit")
    void update(@MappingTarget Target target, FruitFamily fruitFamily);

    default void updateGolden(@MappingTarget GoldenDelicious target, IsFruit fruit) {
        target.setType( "golden updated " + fruit.getType() );
    }

    default void updateApple(@MappingTarget Apple target, IsFruit fruit) {
        target.setType( "apple updated " + fruit.getType() );
    }

    @ObjectFactory
    default GoldenDelicious createGolden() {
        return new GoldenDelicious( "from_object_factory" );
    }

    class Target {
        protected Apple apple;
        protected GoldenDelicious goldenApple;

        public Target(Apple apple, GoldenDelicious goldenApple) {
            this.apple = apple;
            this.goldenApple = goldenApple;
        }

        public Apple getApple() {
            return apple;
        }

        public void setApple(Apple apple) {
            this.apple = apple;
        }

        public GoldenDelicious getGoldenApple() {
            return goldenApple;
        }

        public void setGoldenApple(GoldenDelicious goldenApple) {
            this.goldenApple = goldenApple;
        }
    }
}
