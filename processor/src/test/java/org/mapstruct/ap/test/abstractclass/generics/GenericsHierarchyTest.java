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
package org.mapstruct.ap.test.abstractclass.generics;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("644,687,688")
@WithClasses({
    AbstractAnimal.class,
    AbstractHuman.class,
    GenericsHierarchyMapper.class,
    Key.class,
    KeyOfAllBeings.class,
    AnimalKey.class,
    Identifiable.class,
    GenericIdentifiable.class,
    IAnimal.class,
    Target.class
})
public class GenericsHierarchyTest {

    @Test
    public void determinesAnimalKeyGetter() {
        AbstractAnimal source = new Elephant();

        source.setKey( new AnimalKey() );
        // make sure the jdk compiler resolves the same as we expect
        source.getKey().setTypeParameterIsResolvedToAnimalKey( false );

        Target target = GenericsHierarchyMapper.INSTANCE.toTarget( source );

        assertThat( target.getAnimalKey().typeParameterIsResolvedToAnimalKey() ).isTrue();
        assertThat( target.getAnimalKey().typeParameterIsResolvedToKeyOfAllBeings() ).isFalse();
    }

    @Test
    public void determinesKeyOfAllBeingsGetter() {
        AbstractHuman source = new Child();

        source.setKey( new KeyOfAllBeings() );
        // make sure the jdk compiler resolves the same as we expect
        source.getKey().setTypeParameterIsResolvedToKeyOfAllBeings( false );

        Target target = GenericsHierarchyMapper.INSTANCE.toTarget( source );

        assertThat( target.getKeyOfAllBeings().typeParameterIsResolvedToKeyOfAllBeings() ).isTrue();
    }

    @Test
    public void determinesItemCSourceSetter() {
        Target target = new Target();

        target.setAnimalKey( new AnimalKey() );

        Elephant source = new Elephant();
        GenericsHierarchyMapper.INSTANCE.updateSourceWithAnimalKey( target, source );

        assertThat( source.getKey().typeParameterIsResolvedToAnimalKey() ).isTrue();
    }

    @Test
    public void determinesItemBSourceSetter() {
        Target target = new Target();

        target.setKeyOfAllBeings( new KeyOfAllBeings() );

        Child source = new Child();
        GenericsHierarchyMapper.INSTANCE.updateSourceWithKeyOfAllBeings( target, source );

        assertThat( source.getKey().typeParameterIsResolvedToKeyOfAllBeings() ).isTrue();
    }
}
