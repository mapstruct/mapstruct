/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2245;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-24T17:57:23+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_202 (AdoptOpenJdk)"
)
public class TestMapperImpl implements TestMapper {

    @Override
    public Tenant map(TenantDTO tenant) {
        if ( tenant == null ) {
            return null;
        }

        Tenant tenant1 = new Tenant();

        String id = tenantInnerId( tenant );
        if ( id != null ) {
            tenant1.setId( id );
        }
        else {
            tenant1.setId( "test" );
        }

        return tenant1;
    }

    private String tenantInnerId(TenantDTO tenantDTO) {
        Inner inner = tenantDTO.getInner();
        if ( inner == null ) {
            return null;
        }
        return inner.getId();
    }
}
