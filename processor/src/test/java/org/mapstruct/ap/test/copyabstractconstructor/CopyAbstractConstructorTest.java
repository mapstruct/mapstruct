/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.copyabstractconstructor;

import static java.lang.System.lineSeparator;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses( { AbstractMapper.class, UsesMapper.class, Source.class, Target.class, CSource.class, CTarget.class } )
@WithSpring
public class CopyAbstractConstructorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveConstructor() {
        generatedSource.forMapper( AbstractMapper.class )
            .content()
            .contains( "    public AbstractMapperImpl( String value ) {" + lineSeparator() +
                "        super( value );" );
    }

    @ProcessorTest
    public void springConstructor() {
        generatedSource.forMapper( UsesMapper.class )
            .content()
            .contains( "private final AbstractMapper abstractMapper" )
            .contains( "@Autowired" + lineSeparator() +
                "    public UsesMapperImpl( Integer reference, AbstractMapper abstractMapper ) {"
                + lineSeparator() +
                "        super( reference );" + lineSeparator() +
                "        this.abstractMapper = abstractMapper;" );
    }
}
