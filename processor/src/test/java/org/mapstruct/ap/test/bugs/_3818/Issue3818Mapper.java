package org.mapstruct.ap.test.bugs._3818;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Gaľa
 */
@Mapper
public interface Issue3818Mapper {

    Issue3818Mapper INSTANCE = Mappers.getMapper( Issue3818Mapper.class );

    @Mapping(target = ".", source = "main")
    @Mapping(target = "third", source = "secondary.third")
    Sample map(Sample main, Sample secondary);

    class Sample {
        private String first;

        private String second;

        private String third;

        public Sample(String first, String second, String third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }
    }
}
