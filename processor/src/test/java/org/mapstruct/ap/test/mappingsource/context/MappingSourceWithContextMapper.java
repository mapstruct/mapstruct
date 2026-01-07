/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource.context;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingSource;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MappingSourceWithContextMapper {

    MappingSourceWithContextMapper INSTANCE = Mappers.getMapper( MappingSourceWithContextMapper.class );

    default List<ItemDTO> toDto(Model model) {
        return model
            .getItems()
            .stream()
            .map( item -> this.toDto( item, model.getModelId() ) )
            .collect( Collectors.toList() );
    }

    ItemDTO toDto(Item item, @MappingSource @Context String modelId);

    class Model {
        private List<Item> items;
        private String modelId;

        public Model(String modelId) {
            this.modelId = modelId;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }
    }

    class Item {
        private String itemId;
        private String name;

        public Item(String itemId, String name) {
            this.itemId = itemId;
            this.name = name;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class ItemDTO {
        private String itemId;
        private String name;
        private String modelId; // This comes from @Context parameter

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModelId() {
            return modelId;
        }

        public void setModelId(String modelId) {
            this.modelId = modelId;
        }
    }
}
