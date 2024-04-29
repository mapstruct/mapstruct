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
    FooTarget.class,
    FooSourceNested.class
})
@IssueKey("3133")
public class RedundantNullCheckTest {
    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();
    private final String ls = lineSeparator();

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheckWithAdditionalPrimitiveParameters() {
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
    void shouldNotCreateRedundantNullCheckWithMultipleAdditionalPrimitiveParameters() {
        generatedSource.forMapper( FooMapper.class )
            .content()
            .contains( "@Override" + ls +
                "    public void updateFoo(FooSource input, FooTarget toUpdate, boolean baz, int bay) {" + ls +
                "        if ( input == null ) {" + ls +
                "            return;" + ls +
                "        }" + ls +
                ls +
                "        toUpdate.setBar( input.getBar() );" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheckWithReturnValue() {
        generatedSource.forMapper( FooMapper.class )
            .content()
            .contains( "@Override" + ls +
                "    public FooTarget getUpdatedFooTarget(FooSource input, FooTarget toUpdate) {" + ls +
                "        if ( input == null ) {" + ls +
                "            return toUpdate;" + ls +
                "        }" + ls +
                ls +
                "        toUpdate.setBar( input.getBar() );" + ls +
                ls +
                "        return toUpdate;" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheckWithReturnValueWithAdditionalPrimitiveParameters() {
        generatedSource.forMapper( FooMapper.class )
            .content()
            .contains( "@Override" + ls +
                "    public FooTarget getUpdatedFooTarget(FooSource input, FooTarget toUpdate, boolean baz) {" + ls +
                "        if ( input == null ) {" + ls +
                "            return toUpdate;" + ls +
                "        }" + ls +
                ls +
                "        toUpdate.setBar( input.getBar() );" + ls +
                ls +
                "        return toUpdate;" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheckForNestedSourceWithReturnValueWithAdditionalPrimitiveParameters() {
        generatedSource.forMapper( FooMapper.class )
            .content()
            .containsIgnoringWhitespaces( "@Override" + ls +
                "    public FooTarget map(FooSourceNested input, FooTarget toUpdate, boolean baz) {" + ls +
                "        if ( input == null ) {" + ls +
                "            return toUpdate;" + ls +
                "        }" + ls +
                ls +
                "        toUpdate.setBar( inputNestedBar( input ) );" + ls +
                ls +
                "        return toUpdate;" + ls +
                "    }" + ls +
                "    private String inputNestedBar(FooSourceNested fooSourceNested) {" + ls +
                "        FooSource nested = fooSourceNested.getNested();" + ls +
                "        if ( nested == null ) {" + ls +
                "            return null;" + ls +
                "        }" + ls +
                "        return nested.getBar();" + ls +
                "    }" );
    }

    @ProcessorTest
    @IssueKey("3133")
    void shouldNotCreateRedundantNullCheck() {
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

    @ProcessorTest
    @IssueKey("3133")
    void shouldCreateNullCheckIfNoGuardClauseIsPresentForNestedTargetClass() {
        generatedSource.forMapper( FooMapper.class )
            .content()
            .contains( "@Override" + ls +
                "    public FooTarget map(FooSourceNested source) {" + ls +
                "        if ( source == null ) {" + ls +
                "            return null;" + ls +
                "        }" + ls +
                ls +
                "        FooTarget fooTarget = new FooTarget();" + ls +
                ls +
                "        fooTarget.setBar( source.getBar() );" + ls +
                ls +
                "        return fooTarget;" + ls +
                "    }" );
    }


}
