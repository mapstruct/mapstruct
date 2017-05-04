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
package org.mapstruct.ap.test.array;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.Scientist;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2017-05-03T23:47:43+0200",
    comments = "version: , compiler: javac, environment: Java 1.8.0_131 (Oracle Corporation)"
)
public class ScienceMapperImpl implements ScienceMapper {

    @Override
    public ScientistDto scientistToDto(Scientist scientist) {
        if ( scientist == null ) {
            return null;
        }

        ScientistDto scientistDto = new ScientistDto();

        scientistDto.setName( scientist.getName() );
        String[] publications = scientist.getPublications();
        if ( publications != null ) {
            scientistDto.setPublications( Arrays.copyOf( publications, publications.length ) );
        }
        scientistDto.setPublicationYears( stringArrayTointArray( scientist.getPublicationYears() ) );
        String[] publicPublications = scientist.publicPublications;
        if ( publicPublications != null ) {
            scientistDto.publicPublications = Arrays.copyOf( publicPublications, publicPublications.length );
        }
        scientistDto.publicPublicationYears = stringArrayTointArray( scientist.publicPublicationYears );

        return scientistDto;
    }

    @Override
    public ScientistDto[] scientistsToDtos(Scientist[] scientists) {
        if ( scientists == null ) {
            return null;
        }

        ScientistDto[] scientistDtoTmp = new ScientistDto[scientists.length];
        int i = 0;
        for ( Scientist scientist : scientists ) {
            scientistDtoTmp[i] = scientistToDto( scientist );
            i++;
        }

        return scientistDtoTmp;
    }

    @Override
    public ScientistDto[] scientistsToDtos(List<Scientist> scientists) {
        if ( scientists == null ) {
            return null;
        }

        ScientistDto[] scientistDtoTmp = new ScientistDto[scientists.size()];
        int i = 0;
        for ( Scientist scientist : scientists ) {
            scientistDtoTmp[i] = scientistToDto( scientist );
            i++;
        }

        return scientistDtoTmp;
    }

    @Override
    public List<ScientistDto> scientistsToDtosAsList(Scientist[] scientists) {
        if ( scientists == null ) {
            return null;
        }

        List<ScientistDto> list = new ArrayList<ScientistDto>( scientists.length );
        for ( Scientist scientist : scientists ) {
            list.add( scientistToDto( scientist ) );
        }

        return list;
    }

    @Override
    public ScientistDto[] scientistsToDtos(Scientist[] scientists, ScientistDto[] target) {
        if ( scientists == null ) {
            return null;
        }

        int i = 0;
        for ( Scientist scientist : scientists ) {
            if ( ( i >= target.length ) || ( i >= scientists.length ) ) {
                break;
            }
            target[i] = scientistToDto( scientist );
            i++;
        }

        return target;
    }

    @Override
    public boolean[] nvmMapping(boolean[] source) {
        if ( source == null ) {
            return new boolean[0];
        }

        boolean[] booleanTmp = new boolean[source.length];
        int i = 0;
        for ( boolean boolean1 : source ) {
            booleanTmp[i] = boolean1;
            i++;
        }

        return booleanTmp;
    }

    @Override
    public boolean[] nvmMapping(boolean[] source, boolean[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = false;
            }
            return target;
        }

        int i = 0;
        for ( boolean boolean1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = boolean1;
            i++;
        }

        return target;
    }

    @Override
    public short[] nvmMapping(int[] source, short[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0;
            }
            return target;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = (short) int1;
            i++;
        }

        return target;
    }

    @Override
    public char[] nvmMapping(String[] source, char[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0;
            }
            return target;
        }

        int i = 0;
        for ( String string : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = string.charAt( 0 );
            i++;
        }

        return target;
    }

    @Override
    public int[] nvmMapping(int[] source, int[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0;
            }
            return target;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int1;
            i++;
        }

        return target;
    }

    @Override
    public long[] nvmMapping(int[] source, long[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0L;
            }
            return target;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int1;
            i++;
        }

        return target;
    }

    @Override
    public float[] nvmMapping(int[] source, float[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0.0f;
            }
            return target;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int1;
            i++;
        }

        return target;
    }

    @Override
    public double[] nvmMapping(int[] source, double[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0.0d;
            }
            return target;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int1;
            i++;
        }

        return target;
    }

    @Override
    public String[] nvmMapping(int[] source, String[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = null;
            }
            return target;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = new DecimalFormat( "" ).format( int1 );
            i++;
        }

        return target;
    }

    @Override
    public void nvmMappingVoidReturnNull(int[] source, long[] target) {
        if ( source == null ) {
            return;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int1;
            i++;
        }
    }

    @Override
    public void nvmMappingVoidReturnDefault(int[] source, long[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = 0L;
            }
            return;
        }

        int i = 0;
        for ( int int1 : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int1;
            i++;
        }
    }

    protected int[] stringArrayTointArray(String[] stringArray) {
        if ( stringArray == null ) {
            return null;
        }

        int[] intTmp = new int[stringArray.length];
        int i = 0;
        for ( String string : stringArray ) {
            intTmp[i] = Integer.parseInt( string );
            i++;
        }

        return intTmp;
    }
}
