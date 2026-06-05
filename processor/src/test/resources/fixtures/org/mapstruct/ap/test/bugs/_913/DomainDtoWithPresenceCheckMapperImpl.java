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
    date = "2017-05-06T00:06:21+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class DomainDtoWithPresenceCheckMapperImpl implements DomainDtoWithPresenceCheckMapper {

    private final Helper helper = new Helper();

    @Override
    public Domain create(DtoWithPresenceCheck source) {
        if ( source == null ) {
            return null;
        }

        Domain domain = createNullDomain();

        if ( source.hasStrings() ) {
            List<String> list = source.getStrings();
            domain.setStrings( new LinkedHashSet<>( list ) );
        }
        if ( source.hasStrings() ) {
            domain.setLongs( stringListToLongSet( source.getStrings() ) );
        }
        if ( source.hasStringsInitialized() ) {
            List<String> list1 = source.getStringsInitialized();
            domain.setStringsInitialized( new LinkedHashSet<>( list1 ) );
        }
        if ( source.hasStringsInitialized() ) {
            domain.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
        }
        if ( source.hasStringsWithDefault() ) {
            List<String> list2 = source.getStringsWithDefault();
            domain.setStringsWithDefault( new ArrayList<>( list2 ) );
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

        List<String> strings = source.getStrings();
        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                target.getStrings().clear();
                target.getStrings().addAll( strings );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = strings;
                target.setStrings( new LinkedHashSet<>( list ) );
            }
        }
        List<String> strings1 = source.getStrings();
        if ( target.getLongs() != null ) {
            if ( source.hasStrings() ) {
                target.getLongs().clear();
                target.getLongs().addAll( stringListToLongSet( strings1 ) );
            }
        }
        else {
            if ( source.hasStrings() ) {
                target.setLongs( stringListToLongSet( strings1 ) );
            }
        }
        List<String> stringsInitialized = source.getStringsInitialized();
        if ( target.getStringsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getStringsInitialized().clear();
                target.getStringsInitialized().addAll( stringsInitialized );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                List<String> list1 = stringsInitialized;
                target.setStringsInitialized( new LinkedHashSet<>( list1 ) );
            }
        }
        List<String> stringsInitialized1 = source.getStringsInitialized();
        if ( target.getLongsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getLongsInitialized().clear();
                target.getLongsInitialized().addAll( stringListToLongSet( stringsInitialized1 ) );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                target.setLongsInitialized( stringListToLongSet( stringsInitialized1 ) );
            }
        }
        List<String> stringsWithDefault = source.getStringsWithDefault();
        if ( target.getStringsWithDefault() != null ) {
            if ( source.hasStringsWithDefault() ) {
                target.getStringsWithDefault().clear();
                target.getStringsWithDefault().addAll( stringsWithDefault );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        else {
            if ( source.hasStringsWithDefault() ) {
                List<String> list2 = stringsWithDefault;
                target.setStringsWithDefault( new ArrayList<>( list2 ) );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
    }

    @Override
    public Domain updateWithReturn(DtoWithPresenceCheck source, Domain target) {
        if ( source == null ) {
            return target;
        }

        List<String> strings = source.getStrings();
        if ( target.getStrings() != null ) {
            if ( source.hasStrings() ) {
                target.getStrings().clear();
                target.getStrings().addAll( strings );
            }
        }
        else {
            if ( source.hasStrings() ) {
                List<String> list = strings;
                target.setStrings( new LinkedHashSet<>( list ) );
            }
        }
        List<String> strings1 = source.getStrings();
        if ( target.getLongs() != null ) {
            if ( source.hasStrings() ) {
                target.getLongs().clear();
                target.getLongs().addAll( stringListToLongSet( strings1 ) );
            }
        }
        else {
            if ( source.hasStrings() ) {
                target.setLongs( stringListToLongSet( strings1 ) );
            }
        }
        List<String> stringsInitialized = source.getStringsInitialized();
        if ( target.getStringsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getStringsInitialized().clear();
                target.getStringsInitialized().addAll( stringsInitialized );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                List<String> list1 = stringsInitialized;
                target.setStringsInitialized( new LinkedHashSet<>( list1 ) );
            }
        }
        List<String> stringsInitialized1 = source.getStringsInitialized();
        if ( target.getLongsInitialized() != null ) {
            if ( source.hasStringsInitialized() ) {
                target.getLongsInitialized().clear();
                target.getLongsInitialized().addAll( stringListToLongSet( stringsInitialized1 ) );
            }
        }
        else {
            if ( source.hasStringsInitialized() ) {
                target.setLongsInitialized( stringListToLongSet( stringsInitialized1 ) );
            }
        }
        List<String> stringsWithDefault = source.getStringsWithDefault();
        if ( target.getStringsWithDefault() != null ) {
            if ( source.hasStringsWithDefault() ) {
                target.getStringsWithDefault().clear();
                target.getStringsWithDefault().addAll( stringsWithDefault );
            }
            else {
                target.setStringsWithDefault( helper.toList( "3" ) );
            }
        }
        else {
            if ( source.hasStringsWithDefault() ) {
                List<String> list2 = stringsWithDefault;
                target.setStringsWithDefault( new ArrayList<>( list2 ) );
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

        Set<Long> set = new LinkedHashSet<>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( String string : list ) {
            set.add( Long.parseLong( string ) );
        }

        return set;
    }
}
