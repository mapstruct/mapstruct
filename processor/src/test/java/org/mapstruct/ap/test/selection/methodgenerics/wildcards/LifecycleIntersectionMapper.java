/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.wildcards;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface LifecycleIntersectionMapper {

    LifecycleIntersectionMapper INSTANCE = Mappers.getMapper( LifecycleIntersectionMapper.class );

    @Mapping(target = "realm", ignore = true)
    RealmTarget mapRealm(String source);

    @Mapping(target = "uniqueRealm", ignore = true)
    UniqueRealmTarget mapUniqueRealm(String source);

    @Mapping(target = "realm", ignore = true)
    @Mapping(target = "uniqueRealm", ignore = true)
    BothRealmsTarget mapBothRealms(String source);

    @AfterMapping
    default <T extends RealmObject & UniqueRealmObject> void afterMapping(String source, @MappingTarget T target) {
        target.setRealm( "realm_" + source );
        target.setUniqueRealm( "uniqueRealm_" + source );
    }

    interface RealmObject {
        void setRealm(String realm);
    }

    interface UniqueRealmObject {
        void setUniqueRealm(String realm);
    }

    class RealmTarget implements RealmObject {

        protected String realm;

        public String getRealm() {
            return realm;
        }

        @Override
        public void setRealm(String realm) {
            this.realm = realm;
        }
    }

    class UniqueRealmTarget implements UniqueRealmObject {
        protected String uniqueRealm;

        @Override
        public void setUniqueRealm(String uniqueRealm) {
            this.uniqueRealm = uniqueRealm;
        }

        public String getUniqueRealm() {
            return uniqueRealm;
        }
    }

    class BothRealmsTarget implements RealmObject, UniqueRealmObject {

        protected String realm;
        protected String uniqueRealm;

        public String getRealm() {
            return realm;
        }

        @Override
        public void setRealm(String realm) {
            this.realm = realm;
        }

        public String getUniqueRealm() {
            return uniqueRealm;
        }

        @Override
        public void setUniqueRealm(String uniqueRealm) {
            this.uniqueRealm = uniqueRealm;
        }
    }
}
