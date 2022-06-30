/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass.generics;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

/**
 * @author Andreas Gudian
 *
 */
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

    // Running only with the JDK compiler due to a bug in the Eclipse compiler
    // (https://bugs.eclipse.org/bugs/show_bug.cgi?id=540101)
    // See https://github.com/mapstruct/mapstruct/issues/1553 and https://github.com/mapstruct/mapstruct/pull/1587
    // for more information
    @ProcessorTest(Compiler.JDK)
    public void determinesAnimalKeyGetter() {
        AbstractAnimal source = new Elephant();

        source.setKey( new AnimalKey() );
        // make sure the jdk compiler resolves the same as we expect
        source.getKey().setTypeParameterIsResolvedToAnimalKey( false );

        Target target = GenericsHierarchyMapper.INSTANCE.toTarget( source );

        assertThat( target.getAnimalKey().typeParameterIsResolvedToAnimalKey() ).isTrue();
        assertThat( target.getAnimalKey().typeParameterIsResolvedToKeyOfAllBeings() ).isFalse();
    }

    @ProcessorTest
    public void determinesKeyOfAllBeingsGetter() {
        AbstractHuman source = new Child();

        source.setKey( new KeyOfAllBeings() );
        // make sure the jdk compiler resolves the same as we expect
        source.getKey().setTypeParameterIsResolvedToKeyOfAllBeings( false );

        Target target = GenericsHierarchyMapper.INSTANCE.toTarget( source );

        assertThat( target.getKeyOfAllBeings().typeParameterIsResolvedToKeyOfAllBeings() ).isTrue();
    }

    @ProcessorTest
    public void determinesItemCSourceSetter() {
        Target target = new Target();

        target.setAnimalKey( new AnimalKey() );

        Elephant source = new Elephant();
        GenericsHierarchyMapper.INSTANCE.updateSourceWithAnimalKey( target, source );

        assertThat( source.getKey().typeParameterIsResolvedToAnimalKey() ).isTrue();
    }

    @ProcessorTest
    public void determinesItemBSourceSetter() {
        Target target = new Target();

        target.setKeyOfAllBeings( new KeyOfAllBeings() );

        Child source = new Child();
        GenericsHierarchyMapper.INSTANCE.updateSourceWithKeyOfAllBeings( target, source );

        assertThat( source.getKey().typeParameterIsResolvedToKeyOfAllBeings() ).isTrue();
    }
}
