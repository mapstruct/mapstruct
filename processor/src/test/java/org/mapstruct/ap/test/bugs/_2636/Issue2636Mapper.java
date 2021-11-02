/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2636;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Issue2636Mapper {
    @Mapping( source = "folder.ancestor.id", target = "parent", defaultValue = "#", qualifiedByName = "uuidToString" )
    DirectoryNode convert(Folder folder);

    @Named( "uuidToString" )
    default String uuidToString(UUID id) {
        return id.toString();
    }
}

class DirectoryNode {
    private String parent;

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }
}

class Folder {
    private UUID id;
    private Folder ancestor;

    Folder(UUID id, Folder ancestor) {
        this.id = id;
        this.ancestor = ancestor;
    }

    public Folder getAncestor() {
        return ancestor;
    }

    public UUID getId() {
        return id;
    }
}
