/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-05-06T00:06:20+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class DomainDtoWithNcvsAlwaysMapperImpl implements DomainDtoWithNcvsAlwaysMapper {

    private final Helper helper = new Helper();

    @Override
    public Domain create(DtoWithPresenceCheck source) {
        if ( source == null ) {
            return null;
        }

        Domain domain = createNullDomain();

        if ( source.hasStrings() ) {
            List<String> list = source.getStrings();
            domain.setStrings( new LinkedHashSet<String>( list ) );
        }
        if ( source.hasStrings() ) {
            domain.setLongs( stringListToLongSet( source.getStrings() ) );
        }
        if ( source.hasStringsInitialized() ) {
            List<String> list1 = source.getStringsInitialized();
            domain.setStringsInitialized( new LinkedHashSet<String>( list1 ) );
        }
        if ( source.hasStringsInitialized() ) {
            domain.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
        }
        if ( source.hasStringsWithDefault() ) {
            List<String> list2 = source.getStringsWithDefault();
            domain.setStringsWithDefault( new ArrayList<String>( list2 ) );
        }
        else {
            domain.setStringsWithDefault( helper.toList( "3" ) );
        }

        return domain;
    }

    @Override
    public void update(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return;
        }

        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                target.getStrings().clear();
                target.getStrings().addAll( source.getStrings() );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.setStrings( new LinkedHashSet<String>( list ) );
            }
        }
        if ( target.getLongs() != null ) {
            if ( source.hasStrings() ) {
                target.getLongs().clear();
                target.getLongs().addAll( stringListToLongSet( source.getStrings() ) );
            }
        }
        else {
            if ( source.hasStrings() ) {
                target.setLongs( stringListToLongSet( source.getStrings() ) );
            }
        }
        if ( target.getStringsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getStringsInitialized().clear();
                target.getStringsInitialized().addAll( source.getStringsInitialized() );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                List<String> list1 = source.getStringsInitialized();
                target.setStringsInitialized( new LinkedHashSet<String>( list1 ) );
            }
        }
        if ( target.getLongsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getLongsInitialized().clear();
                target.getLongsInitialized().addAll( stringListToLongSet( source.getStringsInitialized() ) );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                target.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
            }
        }
        if ( target.getStringsWithDefault() != null ) {
            if ( source.hasStringsWithDefault() ) {
                target.getStringsWithDefault().clear();
                target.getStringsWithDefault().addAll( source.getStringsWithDefault() );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        else {
            if ( source.hasStringsWithDefault() ) {
                List<String> list2 = source.getStringsWithDefault();
                target.setStringsWithDefault( new ArrayList<String>( list2 ) );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
    }

    @Override
    public Domain updateWithReturn(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return null;
        }

        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                target.getStrings().clear();
                target.getStrings().addAll( source.getStrings() );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.setStrings( new LinkedHashSet<String>( list ) );
            }
        }
        if ( target.getLongs() != null ) {
            if ( source.hasStrings() ) {
                target.getLongs().clear();
                target.getLongs().addAll( stringListToLongSet( source.getStrings() ) );
            }
        }
        else {
            if ( source.hasStrings() ) {
                target.setLongs( stringListToLongSet( source.getStrings() ) );
            }
        }
        if ( target.getStringsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getStringsInitialized().clear();
                target.getStringsInitialized().addAll( source.getStringsInitialized() );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                List<String> list1 = source.getStringsInitialized();
                target.setStringsInitialized( new LinkedHashSet<String>( list1 ) );
            }
        }
        if ( target.getLongsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getLongsInitialized().clear();
                target.getLongsInitialized().addAll( stringListToLongSet( source.getStringsInitialized() ) );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                target.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
            }
        }
        if ( target.getStringsWithDefault() != null ) {
            if ( source.hasStringsWithDefault() ) {
                target.getStringsWithDefault().clear();
                target.getStringsWithDefault().addAll( source.getStringsWithDefault() );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        else {
            if ( source.hasStringsWithDefault() ) {
                List<String> list2 = source.getStringsWithDefault();
                target.setStringsWithDefault( new ArrayList<String>( list2 ) );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }

        return target;
    }

    protected Set<Long> stringListToLongSet(List<String> list) {
        if ( list == null ) {
            return null;
        }

        Set<Long> set = new LinkedHashSet<Long>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( String string : list ) {
            set.add( Long.parseLong( string ) );
        }

        return set;
    }
}
