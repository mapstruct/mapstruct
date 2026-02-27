/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3992;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Soumik Sarker
 */
@Mapper
public interface MultiSourceWithPrimitiveMapper {

    MultiSourceWithPrimitiveMapper INSTANCE = Mappers.getMapper( MultiSourceWithPrimitiveMapper.class );

    Target map(String sku, String name, Long categoryId, int size, int pageNumber, boolean draft);

    class Target {
        private String sku;
        private String name;
        private Long categoryId;
        private int size;
        private int pageNumber;
        private boolean draft;

        // Getters and Setters
        public String getSku() { return sku; }
        public void setSku(String sku) { this.sku = sku; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Long getCategoryId() { return categoryId; }
        public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        public int getPageNumber() { return pageNumber; }
        public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }
        public boolean isDraft() { return draft; }
        public void setDraft(boolean draft) { this.draft = draft; }
    }
}
