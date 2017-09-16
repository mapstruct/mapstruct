/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.bugs._913;

import java.util.ArrayList;
import java.util.HashSet;
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
            domain.setLongsInitialized( stringListToLongSet( source.getStringsInitialized() ) );
            domain.setLongs( stringListToLongSet( source.getStrings() ) );
            List<String> list = source.getStrings();
            if ( list != null ) {
                domain.setStrings( new HashSet<String>( list ) );
            }
            else {
                domain.setStrings( new HashSet<String>() );
            }
            List<String> list1 = source.getStringsWithDefault();
            if ( list1 != null ) {
                domain.setStringsWithDefault( new ArrayList<String>( list1 ) );
            }
            else {
                domain.setStringsWithDefault( helper.toList( "3" ) );
            }
            List<String> list2 = source.getStringsInitialized();
            if ( list2 != null ) {
                domain.setStringsInitialized( new HashSet<String>( list2 ) );
            }
            else {
                domain.setStringsInitialized( new HashSet<String>() );
            }
        }

        return domain;
    }

    @Override
    public void update(Dto source, Domain target) {

        if ( source != null ) {
            if ( target.getLongs() != null ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.getLongs().clear();
                    target.getLongs().addAll( set );
                }
                else {
                    target.setLongs( new HashSet<Long>() );
                }
            }
            else {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.setLongs( set );
                }
            }
            if ( target.getStrings() != null ) {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.getStrings().clear();
                    target.getStrings().addAll( list );
                }
                else {
                    target.setStrings( new HashSet<String>() );
                }
            }
            else {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.setStrings( new HashSet<String>( list ) );
                }
            }
            if ( target.getLongsInitialized() != null ) {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.getLongsInitialized().clear();
                    target.getLongsInitialized().addAll( set1 );
                }
                else {
                    target.setLongsInitialized( new HashSet<Long>() );
                }
            }
            else {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.setLongsInitialized( set1 );
                }
            }
            if ( target.getStringsWithDefault() != null ) {
                List<String> list1 = source.getStringsWithDefault();
                if ( list1 != null ) {
                    target.getStringsWithDefault().clear();
                    target.getStringsWithDefault().addAll( list1 );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
            else {
                List<String> list1 = source.getStringsWithDefault();
                if ( list1 != null ) {
                    target.setStringsWithDefault( new ArrayList<String>( list1 ) );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
            if ( target.getStringsInitialized() != null ) {
                List<String> list2 = source.getStringsInitialized();
                if ( list2 != null ) {
                    target.getStringsInitialized().clear();
                    target.getStringsInitialized().addAll( list2 );
                }
                else {
                    target.setStringsInitialized( new HashSet<String>() );
                }
            }
            else {
                List<String> list2 = source.getStringsInitialized();
                if ( list2 != null ) {
                    target.setStringsInitialized( new HashSet<String>( list2 ) );
                }
            }
        }
    }

    @Override
    public Domain updateWithReturn(Dto source, Domain target) {

        if ( source != null ) {
            if ( target.getLongs() != null ) {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.getLongs().clear();
                    target.getLongs().addAll( set );
                }
                else {
                    target.setLongs( new HashSet<Long>() );
                }
            }
            else {
                Set<Long> set = stringListToLongSet( source.getStrings() );
                if ( set != null ) {
                    target.setLongs( set );
                }
            }
            if ( target.getStrings() != null ) {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.getStrings().clear();
                    target.getStrings().addAll( list );
                }
                else {
                    target.setStrings( new HashSet<String>() );
                }
            }
            else {
                List<String> list = source.getStrings();
                if ( list != null ) {
                    target.setStrings( new HashSet<String>( list ) );
                }
            }
            if ( target.getLongsInitialized() != null ) {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.getLongsInitialized().clear();
                    target.getLongsInitialized().addAll( set1 );
                }
                else {
                    target.setLongsInitialized( new HashSet<Long>() );
                }
            }
            else {
                Set<Long> set1 = stringListToLongSet( source.getStringsInitialized() );
                if ( set1 != null ) {
                    target.setLongsInitialized( set1 );
                }
            }
            if ( target.getStringsWithDefault() != null ) {
                List<String> list1 = source.getStringsWithDefault();
                if ( list1 != null ) {
                    target.getStringsWithDefault().clear();
                    target.getStringsWithDefault().addAll( list1 );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
            else {
                List<String> list1 = source.getStringsWithDefault();
                if ( list1 != null ) {
                    target.setStringsWithDefault( new ArrayList<String>( list1 ) );
                }
                else {
                    target.setStringsWithDefault( helper.toList( "3" ) );
                }
            }
            if ( target.getStringsInitialized() != null ) {
                List<String> list2 = source.getStringsInitialized();
                if ( list2 != null ) {
                    target.getStringsInitialized().clear();
                    target.getStringsInitialized().addAll( list2 );
                }
                else {
                    target.setStringsInitialized( new HashSet<String>() );
                }
            }
            else {
                List<String> list2 = source.getStringsInitialized();
                if ( list2 != null ) {
                    target.setStringsInitialized( new HashSet<String>( list2 ) );
                }
            }
        }

        return target;
    }

    protected Set<Long> stringListToLongSet(List<String> list) {
        if ( list == null ) {
            return new HashSet<Long>();
        }

        Set<Long> set = new HashSet<Long>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( String string : list ) {
            set.add( Long.parseLong( string ) );
        }

        return set;
    }
}
