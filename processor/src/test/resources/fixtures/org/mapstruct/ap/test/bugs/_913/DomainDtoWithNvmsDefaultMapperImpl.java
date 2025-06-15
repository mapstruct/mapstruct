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
public class DomainDtoWithNvmsDefaultMapperImpl implements DomainDtoWithNvmsDefaultMapper {

    private final Helper helper = new Helper();

    @Override
    public Domain create(Dto source) {

        Domain domain = new Domain();

        if ( source != null ) {
            List<String> list = source.getStrings();
            if ( list != null ) {
                domain.setStrings( new LinkedHashSet<String>( list ) );
            }
            domain.setLongs( stringListToLongSet( source.getStrings() ) );
            List<String> list1 = source.getStringsInitialized();
            if ( list1 != null ) {
                domain.setStringsInitialized( new LinkedHashSet<String>( list1 ) );
            }
            domain.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
            List<String> list2 = source.getStringsWithDefault();
            if ( list2 != null ) {
                domain.setStringsWithDefault( new ArrayList<String>( list2 ) );
            }
            else {
                domain.setStringsWithDefault( helper.toList( "3" ) );
            }
        }

        return domain;
    }

    @Override
    public void update(Dto source, Domain target) {

        if ( source != null ) {
            if ( target.getStrings() != null ) {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.getStrings().clear();
                    target.getStrings().addAll( list );
                }
                else {
                    target.setStrings( new LinkedHashSet<String>() );
                }
            }
            else {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.setStrings( new LinkedHashSet<String>( list ) );
                }
                else {
                    target.setStrings( new LinkedHashSet<String>() );
                }
            }
            if ( target.getLongs() != null ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.getLongs().clear();
                    target.getLongs().addAll( set );
                }
                else {
                    target.setLongs( new LinkedHashSet<Long>() );
                }
            }
            else {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.setLongs( set );
                }
                else {
                    target.setLongs( new LinkedHashSet<Long>() );
                }
            }
            if ( target.getStringsInitialized() != null ) {
                List<String> list1 = source.getStringsInitialized();
                if ( list1 != null ) {
                    target.getStringsInitialized().clear();
                    target.getStringsInitialized().addAll( list1 );
                }
                else {
                    target.setStringsInitialized( new LinkedHashSet<String>() );
                }
            }
            else {
                List<String> list1 = source.getStringsInitialized();
                if ( list1 != null ) {
                    target.setStringsInitialized( new LinkedHashSet<String>( list1 ) );
                }
                else {
                    target.setStringsInitialized( new LinkedHashSet<String>() );
                }
            }
            if ( target.getLongsInitialized() != null ) {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.getLongsInitialized().clear();
                    target.getLongsInitialized().addAll( set1 );
                }
                else {
                    target.setLongsInitialized( new LinkedHashSet<Long>() );
                }
            }
            else {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.setLongsInitialized( set1 );
                }
                else {
                    target.setLongsInitialized( new LinkedHashSet<Long>() );
                }
            }
            if ( target.getStringsWithDefault() != null ) {
                List<String> list2 = source.getStringsWithDefault();
                if ( list2 != null ) {
                    target.getStringsWithDefault().clear();
                    target.getStringsWithDefault().addAll( list2 );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
            else {
                List<String> list2 = source.getStringsWithDefault();
                if ( list2 != null ) {
                    target.setStringsWithDefault( new ArrayList<String>( list2 ) );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
        }
    }

    @Override
    public Domain updateWithReturn(Dto source, Domain target) {

        if ( source != null ) {
            if ( target.getStrings() != null ) {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.getStrings().clear();
                    target.getStrings().addAll( list );
                }
                else {
                    target.setStrings( new LinkedHashSet<String>() );
                }
            }
            else {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.setStrings( new LinkedHashSet<String>( list ) );
                }
                else {
                    target.setStrings( new LinkedHashSet<String>() );
                }
            }
            if ( target.getLongs() != null ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.getLongs().clear();
                    target.getLongs().addAll( set );
                }
                else {
                    target.setLongs( new LinkedHashSet<Long>() );
                }
            }
            else {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.setLongs( set );
                }
                else {
                    target.setLongs( new LinkedHashSet<Long>() );
                }
            }
            if ( target.getStringsInitialized() != null ) {
                List<String> list1 = source.getStringsInitialized();
                if ( list1 != null ) {
                    target.getStringsInitialized().clear();
                    target.getStringsInitialized().addAll( list1 );
                }
                else {
                    target.setStringsInitialized( new LinkedHashSet<String>() );
                }
            }
            else {
                List<String> list1 = source.getStringsInitialized();
                if ( list1 != null ) {
                    target.setStringsInitialized( new LinkedHashSet<String>( list1 ) );
                }
                else {
                    target.setStringsInitialized( new LinkedHashSet<String>() );
                }
            }
            if ( target.getLongsInitialized() != null ) {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.getLongsInitialized().clear();
                    target.getLongsInitialized().addAll( set1 );
                }
                else {
                    target.setLongsInitialized( new LinkedHashSet<Long>() );
                }
            }
            else {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.setLongsInitialized( set1 );
                }
                else {
                    target.setLongsInitialized( new LinkedHashSet<Long>() );
                }
            }
            if ( target.getStringsWithDefault() != null ) {
                List<String> list2 = source.getStringsWithDefault();
                if ( list2 != null ) {
                    target.getStringsWithDefault().clear();
                    target.getStringsWithDefault().addAll( list2 );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
            else {
                List<String> list2 = source.getStringsWithDefault();
                if ( list2 != null ) {
                    target.setStringsWithDefault( new ArrayList<String>( list2 ) );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
        }

        return target;
    }

    protected Set<Long> stringListToLongSet(List<String> list) {
        if ( list == null ) {
            return new LinkedHashSet<Long>();
        }

        Set<Long> set = new LinkedHashSet<Long>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( String string : list ) {
            set.add( Long.parseLong( string ) );
        }

        return set;
    }
}
