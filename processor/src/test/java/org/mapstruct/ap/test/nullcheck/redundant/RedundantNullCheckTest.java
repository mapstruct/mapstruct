/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.redundant;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;

@WithClasses({
    FooMapper.class,
    FooMapperConfigured.class,
    FooSource.class,
    FooTarget.class
})
@IssueKey("3133")
public class RedundantNullCheckTest {
    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheckWithAdditionalPrimitiveParameters() {
        String ls = lineSeparator();
        generatedSource.forMapper( FooMapper.class )
            .content()
            .contains( "@Override" + ls +
                "    public void updateFoo(FooSource input, FooTarget toUpdate, boolean baz) {" + ls +
                "        if ( input == null ) {" + ls +
                "            return;" + ls +
                "        }" + ls +
                ls +
                "        toUpdate.setBar( input.getBar() );" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheck() {
        String ls = lineSeparator();
        generatedSource.forMapper( FooMapper.class )
            .content()
            .contains( "@Override" + ls +
                "    public void updateFoo(FooSource input, FooTarget toUpdate) {" + ls +
                "        if ( input == null ) {" + ls +
                "            return;" + ls +
                "        }" + ls +
                ls +
                "        toUpdate.setBar( input.getBar() );" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldCreateNullCheckIfNoGuardClauseIsPresentWithAdditionalPrimitiveParameters() {
        String ls = lineSeparator();
        generatedSource.forMapper( FooMapperConfigured.class )
            .content()
            .contains( "@Override" + ls +
                "    public void updateFoo(FooSource input, FooTarget toUpdate, boolean baz) {" + ls +
                ls +
                "        if ( input != null ) {" + ls +
                "            toUpdate.setBar( input.getBar() );" + ls +
                "        }" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldCreateNullCheckIfNoGuardClauseIsPresent() {
        String ls = lineSeparator();
        generatedSource.forMapper( FooMapperConfigured.class )
            .content()
            .contains( "@Override" + ls +
                "    public void updateFoo(FooSource input, FooTarget toUpdate) {" + ls +
                ls +
                "        if ( input != null ) {" + ls +
                "            toUpdate.setBar( input.getBar() );" + ls +
                "        }" + ls +
                "    }" );
    }

}
