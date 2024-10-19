/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3747;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-19T17:01:16+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.4 (Arch Linux)"
)
public class ICategoryMapperImpl implements ICategoryMapper {

    @Override
    public ApiCategoryRecord mapRecord(Category category) {

        Long id = null;
        String title = null;
        if ( category != null ) {
            id = category.getId();
            title = category.getTitle();
        }

        ApiCategoryRecord apiCategoryRecord = new ApiCategoryRecord( id, title );

        return apiCategoryRecord;
    }
}