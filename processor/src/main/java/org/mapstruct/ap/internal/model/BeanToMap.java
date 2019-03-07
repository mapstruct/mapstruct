package org.mapstruct.ap.internal.model;

import java.util.List;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

public class BeanToMap {

    private final List<MapEntry> mapEntries;
    private final String className;


    public BeanToMap(String className, List<MapEntry> types) {
        this.className = className;
        this.mapEntries = types;
    }

    public String getClassName() {
        return className;
    }

    public List<MapEntry> getMapEntries() {
        return mapEntries;
    }

    public String capitalize(String in) {
        return Strings.capitalize( in );
    }

    public static class MapEntry {

        private final String name;
        private final Type type;

        public MapEntry(String name, Type type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }
    }
}
