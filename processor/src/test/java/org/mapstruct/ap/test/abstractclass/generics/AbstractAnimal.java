/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass.generics;

/**
 * @author Andreas Gudian
 */
public abstract class AbstractAnimal
    implements Identifiable<KeyOfAllBeings>, IAnimal<AnimalKey> {

}
