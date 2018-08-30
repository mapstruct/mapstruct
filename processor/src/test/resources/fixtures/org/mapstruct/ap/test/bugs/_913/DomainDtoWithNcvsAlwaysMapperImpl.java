/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.HashSet;
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

        Domain domain = new Domain();

        if ( source.hasStringsInitialized() ) {
            domain.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
        }
        if ( source.hasStrings() ) {
            domain.setLongs( stringListToLongSet( source.getStrings() ) );
        }
        if ( source.hasStrings() ) {
            List<String> list = source.getStrings();
            domain.setStrings( new HashSet<String>( list ) );
        }
        if ( source.hasStringsWithDefault() ) {
            List<String> list1 = source.getStringsWithDefault();
            domain.setStringsWithDefault( new ArrayList<String>( list1 ) );
        }
        else {
            domain.setStringsWithDefault( helper.toList( "3" ) );
        }
        if ( source.hasStringsInitialized() ) {
            List<String> list2 = source.getStringsInitialized();
            domain.setStringsInitialized( new HashSet<String>( list2 ) );
        }

        return domain;
    }

    @Override
    public void update(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return;
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
        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                target.getStrings().clear();
                target.getStrings().addAll( source.getStrings() );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.setStrings( new HashSet<String>( list ) );
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
                List<String> list1 = source.getStringsWithDefault();
                target.setStringsWithDefault( new ArrayList<String>( list1 ) );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
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
                List<String> list2 = source.getStringsInitialized();
                target.setStringsInitialized( new HashSet<String>( list2 ) );
            }
        }
    }

    @Override
    public Domain updateWithReturn(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return null;
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
        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                target.getStrings().clear();
                target.getStrings().addAll( source.getStrings() );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = source.getStrings();
                target.setStrings( new HashSet<String>( list ) );
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
                List<String> list1 = source.getStringsWithDefault();
                target.setStringsWithDefault( new ArrayList<String>( list1 ) );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
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
                List<String> list2 = source.getStringsInitialized();
                target.setStringsInitialized( new HashSet<String>( list2 ) );
            }
        }

        return target;
    }

    protected Set<Long> stringListToLongSet(List<String> list) {
        if ( list == null ) {
            return null;
        }

        Set<Long> set = new HashSet<Long>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( String string : list ) {
            set.add( Long.parseLong( string ) );
        }

        return set;
    }
}
