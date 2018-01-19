package org.mapstruct.ap.test.source.defaultExpressions.java.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.source.defaultExpressions.java.Source;
import org.mapstruct.ap.test.source.defaultExpressions.java.Target;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.UUID;

/**
 * @author Jeffrey Smyth
 */
@Mapper( imports = {UUID.class, Date.class } )
public interface DefaultExpressionExpressionMapper {

    DefaultExpressionExpressionMapper INSTANCE = Mappers.getMapper( DefaultExpressionExpressionMapper.class );

    @Mappings ( {
        @Mapping ( target = "sourceId", expression = "java( UUID.randomUUID().toString() )", defaultExpression = "java( UUID.randomUUID().toString() )" ),
        @Mapping( target = "sourceDate", source = "date", defaultExpression = "java( new Date())")
    } )
    Target sourceToTarget( Source s );
}
