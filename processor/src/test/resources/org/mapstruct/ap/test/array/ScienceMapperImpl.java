package org.mapstruct.ap.test.array;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.ap.test.array._target.ScientistDto;
import org.mapstruct.ap.test.array.source.Scientist;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class ScienceMapperImpl implements ScienceMapper {

    @Override
    public ScientistDto scientistToDto(Scientist scientist) {
        if ( scientist == null ) {
            return null;
        }

        ScientistDto scientistDto = new ScientistDto();

        scientistDto.setName( scientist.getName() );
        if ( scientist.getPublications() != null ) {
            java.lang.String[] publications = scientist.getPublications();
            scientistDto.setPublications( Arrays.copyOf( publications, publications.length ) );
        }
        scientistDto.setPublicationYears( stringArrayTointArray( scientist.getPublicationYears() ) );

        return scientistDto;
    }

    @Override
    public org.mapstruct.ap.test.array._target.ScientistDto[] scientistsToDtos(org.mapstruct.ap.test.array.source.Scientist[] scientists) {
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
    public org.mapstruct.ap.test.array._target.ScientistDto[] scientistsToDtos(List<Scientist> scientists) {
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
    public List<ScientistDto> scientistsToDtosAsList(org.mapstruct.ap.test.array.source.Scientist[] scientists) {
        if ( scientists == null ) {
            return null;
        }

        List<ScientistDto> list = new ArrayList<ScientistDto>();
        for ( Scientist scientist : scientists ) {
            list.add( scientistToDto( scientist ) );
        }

        return list;
    }

    @Override
    public org.mapstruct.ap.test.array._target.ScientistDto[] scientistsToDtos(org.mapstruct.ap.test.array.source.Scientist[] scientists, org.mapstruct.ap.test.array._target.ScientistDto[] target) {
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
        for ( boolean boolean_ : source ) {
            booleanTmp[i] = boolean_;
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
        for ( boolean boolean_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = boolean_;
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = (short) int_;
            i++;
        }

        return target;
    }

    @Override
    public char[] nvmMapping(java.lang.String[] source, char[] target) {
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }

        return target;
    }

    @Override
    public java.lang.String[] nvmMapping(int[] source, java.lang.String[] target) {
        if ( source == null ) {
            for (int j = 0; j < target.length; j++ ) {
                target[j] = null;
            }
            return target;
        }

        int i = 0;
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = new DecimalFormat( "" ).format( int_ );
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
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
        for ( int int_ : source ) {
            if ( ( i >= target.length ) || ( i >= source.length ) ) {
                break;
            }
            target[i] = int_;
            i++;
        }
    }

    protected int[] stringArrayTointArray(java.lang.String[] stringArray) {
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
