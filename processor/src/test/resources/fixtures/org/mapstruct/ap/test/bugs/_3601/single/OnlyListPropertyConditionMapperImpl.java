/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601.single;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.bugs._3601.Source;
import org.mapstruct.ap.test.bugs._3601.Target;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-03T22:05:12+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class OnlyListPropertyConditionMapperImpl implements OnlyListPropertyConditionMapper {

    @Override
    public Target map(Source source, List<String> sourceIds) {
        if ( source == null && sourceIds == null ) {
            return null;
        }

        Target target = new Target();

        if ( source != null ) {
            target.currentId = source.getUuid();
        }
        if ( isNotEmpty( sourceIds ) ) {
            List<String> list = sourceIds;
            target.targetIds = new ArrayList<String>( list );
        }

        return target;
    }
}
