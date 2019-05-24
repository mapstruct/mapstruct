/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1159;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.mapstruct.ap.internal.util.AnnotationProcessorContext;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;

/**
 * @author Jorge Valencia
 */
public class Issue1159Test {

    @Test
    public void testFindAstModifyingAnnotationProcessors() throws Exception {
        assertThat( new AnnotationProcessorContextWrapper().findProcessorsWrapper() ).isEmpty();
    }

    public static class AnnotationProcessorContextWrapper extends AnnotationProcessorContext {
        public AnnotationProcessorContextWrapper() {
            super( null, null, null, false );
        }

        public List<AstModifyingAnnotationProcessor> findProcessorsWrapper() {
            return findAstModifyingAnnotationProcessors();
        }

        protected boolean hasMoreAnnotationProcessors(Iterator<AstModifyingAnnotationProcessor> it) {
            Iterator<AstModifyingAnnotationProcessor> failingIterator =
                    new Iterator<AstModifyingAnnotationProcessor>(  ) {
                @Override
                public boolean hasNext() {
                    throw new NoClassDefFoundError();
                }

                @Override
                public AstModifyingAnnotationProcessor next() {
                    return null;
                }

            };

            return super.hasMoreAnnotationProcessors( failingIterator );
        }

    }
}
