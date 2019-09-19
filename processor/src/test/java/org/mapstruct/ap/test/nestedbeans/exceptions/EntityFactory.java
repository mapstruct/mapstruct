/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exceptions;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * @author Filip Hrisafov
 * @author Darren Rambaud
 */
public class EntityFactory {

    @ObjectFactory
    public <T> T createEntity(@TargetType Class<T> entityClass) throws MappingException {
        T entity;

        try {
            entity = entityClass.newInstance();
        }
        catch ( IllegalAccessException | InstantiationException exception ) {
            throw new MappingException( "Rare exception thrown, refer to stack trace", exception );
        }
        catch ( Exception exception ) {
            throw new MappingException( "I don't know how you got here, refer to stack trace", exception );
        }

        return entity;
    }
}
