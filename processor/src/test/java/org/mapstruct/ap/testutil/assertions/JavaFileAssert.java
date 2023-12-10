/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.assertions;

import org.assertj.core.api.FileAssert;
import org.assertj.core.error.ShouldHaveSameContent;
import org.assertj.core.internal.Diff;
import org.assertj.core.internal.Failures;
import org.assertj.core.util.diff.Delta;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Allows to perform assertions on .java source files.
 *
 * @author Andreas Gudian
 */
public class JavaFileAssert extends FileAssert {

    private static final String FIRST_LINE_LICENSE_REGEX = ".*Copyright MapStruct Authors.*";
    private static final String GENERATED_DATE_REGEX = "\\s+date = " +
        "\"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{4}\",";
    private static final String GENERATED_COMMENTS_REGEX = "\\s+comments = \"version: , compiler: .*, environment: " +
        ".*\"";
    private static final String IMPORT_GENERATED_ANNOTATION_REGEX = "import javax\\.annotation\\.(processing\\.)?" +
        "Generated;";

    private final Diff diff = new Diff();

    /**
     * @param actual the actual file
     */
    public JavaFileAssert(File actual) {
        super( actual );
    }

    /**
     * Verifies that the specified class is imported in this Java file
     *
     * @param importedClass the class expected to be imported in this Java file
     */
    public void containsImportFor(Class<?> importedClass) {

        content().contains( getClassImportDeclaration( importedClass ) );
    }

    /**
     * Verifies that the specified class is not imported in this Java file
     *
     * @param importedClass the class expected not to be imported in this Java file
     */
    public void containsNoImportFor(Class<?> importedClass) {
        content().doesNotContain( getClassImportDeclaration( importedClass ) );
    }

    /**
     * Verifies that the expected file has the same content as this Java file. The verification ignores
     * the license header and the date/comments line from the {@code @Generated} annotation.
     *
     * @param expected the file that should be matched
     */
    public void hasSameMapperContent(File expected) {
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<Delta<String>> diffs = new ArrayList<>( this.diff.diff(
                actual,
                charset,
                expected,
                charset
            ) );
            diffs.removeIf( this::ignoreDelta );
            if ( !diffs.isEmpty() ) {
                throw Failures.instance()
                    .failure( info, ShouldHaveSameContent.shouldHaveSameContent( actual, expected, diffs ) );
            }
        }
        catch ( IOException e ) {
            throw new UncheckedIOException( format(
                "Unable to compare contents of files:<%s> and:<%s>",
                actual,
                expected
            ), e );
        }
    }

    /**
     * Checks if the delta should be ignored. The delta is ignored if it is a deletion type for the license header
     * or if it is a change delta for the date/comments part of a {@code @Generated} annotation.
     *
     * @param delta that needs to be checked
     * @return {@code true} if this delta should be ignored, {@code false} otherwise
     */
    private boolean ignoreDelta(Delta<String> delta) {
        if ( delta.getType() == Delta.TYPE.DELETE ) {
            List<String> lines = delta.getOriginal().getLines();
            return lines.size() > 2 && lines.get( 1 ).matches( FIRST_LINE_LICENSE_REGEX );
        }
        else if ( delta.getType() == Delta.TYPE.CHANGE ) {
            List<String> lines = delta.getOriginal().getLines();
            if ( lines.size() == 1 ) {
                return lines.get( 0 ).matches( GENERATED_DATE_REGEX ) ||
                    lines.get( 0 ).matches( IMPORT_GENERATED_ANNOTATION_REGEX );
            }
            else if ( lines.size() == 2 ) {
                return lines.get( 0 ).matches( GENERATED_DATE_REGEX ) &&
                    lines.get( 1 ).matches( GENERATED_COMMENTS_REGEX );
            }
        }

        return false;
    }

    /**
     * Build a class import declaration string.
     *
     * @param importedClass
     * @return
     */
    private String getClassImportDeclaration(Class<?> importedClass) {
        String classname = importedClass.getName();
        if ( importedClass.isMemberClass() ) {
            // Member-Class name: a.b.Outer$Inner
            // Import declaration: import a.b.Outer.Inner
            classname = classname.replace( '$', '.' );
        }

        return "import " + classname + ";";
    }
}
