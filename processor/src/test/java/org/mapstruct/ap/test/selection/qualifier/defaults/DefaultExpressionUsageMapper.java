/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.defaults;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface DefaultExpressionUsageMapper {
    @Mapping( source = "folder.ancestor.id", target = "parent",
              defaultExpression = "java(\"#\")", qualifiedByName = "uuidToString" )
    DirectoryNode convert(Folder folder);

    @Named( "uuidToString" )
    default String uuidToString(UUID id) {
        return id.toString();
    }
}
