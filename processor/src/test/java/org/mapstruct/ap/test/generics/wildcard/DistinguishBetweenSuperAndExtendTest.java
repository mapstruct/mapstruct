/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DistinguishBetweenSuperAndExtendTest {

    @ProcessorTest
    @WithClasses({
            SimpleObject.class,
            CollectionExtendTypes.class,
            CollectionSuperTypes.class,
            ErroneousCollectionSuperToExtendMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                            message = "Can't map property " +
                                    "\"Collection<? super org.mapstruct.ap.test.generics.wildcard.SimpleObject> " +
                                    "simpleObjectsCollection\" to " +
                                    "\"Collection<? extends org.mapstruct.ap.test.generics.wildcard.SimpleObject> " +
                                    "simpleObjectsCollection\". Consider to declare/implement a mapping method: " +
                                    "\"Collection<? extends org.mapstruct.ap.test.generics.wildcard.SimpleObject> " +
                                    "map(Collection<? super org.mapstruct.ap.test.generics.wildcard.SimpleObject>" +
                                    " value)\".")
            })
    public void unabaleToMapSuperToExtendforCollection() {

    }

    @ProcessorTest
    @WithClasses({
            SimpleObject.class,
            ErroneousStreamSuperToExtendMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(kind = javax.tools.Diagnostic.Kind.ERROR,
                            message = "Can't map property " +
                                    "\"Stream<? super org.mapstruct.ap.test.generics.wildcard.SimpleObject> " +
                                    "simpleObjectsStream\" to " +
                                    "\"Stream<? extends org.mapstruct.ap.test.generics.wildcard.SimpleObject> " +
                                    "simpleObjectsStream\". Consider to declare/implement a mapping method: " +
                                    "\"Stream<? extends org.mapstruct.ap.test.generics.wildcard.SimpleObject> " +
                                    "map(Stream<? super org.mapstruct.ap.test.generics.wildcard.SimpleObject>" +
                                    " value)\".")
            })
    public void unabaleToMapSuperToExtendforStream() {

    }

    @ProcessorTest
    @WithClasses({
            SimpleObject.class,
            CollectionExtendTypes.class,
            CollectionSuperTypes.class,
            CollectionExtendToSuperMapper.class
    })
    public void allowAssignmentOfExtendBoundToSuperBound() {
        CollectionExtendTypes collectionExtendTypes = new CollectionExtendTypes();
        List<SimpleObject> simpleObjects = new ArrayList<>();
        simpleObjects.add( new SimpleObject() );
        collectionExtendTypes.setSimpleObjectsCollection( simpleObjects );
        CollectionSuperTypes result = CollectionExtendToSuperMapper.INSTANCE.toSuper( collectionExtendTypes );
        assertThat( result ).isNotNull();
        assertThat( result.getSimpleObjectsCollection() ).hasSize( 1 );
    }
}
