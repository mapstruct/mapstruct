/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.assertions;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.regex.Pattern;

import org.assertj.core.api.FileAssert;
import org.assertj.core.error.ShouldBeEqual;
import org.assertj.core.internal.Failures;

import static java.lang.String.format;

/**
 * Allows to perform assertions on .java source files.
 *
 * @author Andreas Gudian
 */
public class JavaFileAssert extends FileAssert {

    private static final String FIRST_LINE_LICENSE_REGEX = ".*Copyright MapStruct Authors.*";
    private static final String GENERATED_DATE_REGEX = "\\s+date = " +
        "\"\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}[\\+\\-]\\d{4}\",";
    private static final String GENERATED_COMMENTS_REGEX = "\\s+comments = \"version: , compiler: .*, environment: " +
        ".*\"";
    private static final String IMPORT_GENERATED_ANNOTATION_REGEX = "import javax\\.annotation\\.(processing\\.)?" +
        "Generated;";

    private static final Pattern LICENSE_PATTERN = Pattern.compile( FIRST_LINE_LICENSE_REGEX );
    private static final Pattern GENERATED_DATE_PATTERN = Pattern.compile( GENERATED_DATE_REGEX );
    private static final Pattern GENERATED_COMMENTS_PATTERN = Pattern.compile( GENERATED_COMMENTS_REGEX );
    private static final Pattern IMPORT_GENERATED_PATTERN = Pattern.compile( IMPORT_GENERATED_ANNOTATION_REGEX );

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
        try {
            Path actualPath = actual.toPath();
            Path expectedPath = expected.toPath();

            String formatterRegex = ".*private final DateTimeFormatter dateTimeFormatter_.*";

            List<String> expectedLines = Files.readAllLines( expectedPath, StandardCharsets.UTF_8 );
            List<String> actualLines = Files.readAllLines( actualPath, StandardCharsets.UTF_8 );

            List<String> expectedFormatters = new ArrayList<>();
            List<String> expectedOtherLines = new ArrayList<>();

            for ( String line : expectedLines ) {
                if ( line.matches( formatterRegex ) ) {
                    expectedFormatters.add( line );
                }
                else if ( isIgnorableLine( line ) ) {
                    expectedOtherLines.add( line );
                }
            }

            List<String> actualFormatters = new ArrayList<>();
            List<String> actualOtherLines = new ArrayList<>();

            for ( String line : actualLines ) {
                if ( line.matches( formatterRegex ) ) {
                    actualFormatters.add( line );
                }
                else if ( isIgnorableLine( line ) ) {
                    actualOtherLines.add( line );
                }
            }

            // Sort the formatter lines to make the order deterministic
            Collections.sort( expectedFormatters );
            Collections.sort( actualFormatters );

            String expectedOtherContent = String.join( "\n", expectedOtherLines ).replaceAll( "\\r\\n", "\n" );
            String actualOtherContent = String.join( "\n", actualOtherLines ).replaceAll( "\\r\\n", "\n" );

            String expectedFormatterContent = String.join( "\n", expectedFormatters ).replaceAll( "\\r\\n", "\n" );
            String actualFormatterContent = String.join( "\n", actualFormatters ).replaceAll( "\\r\\n", "\n" );

            if ( !expectedOtherContent.equals( actualOtherContent ) ) {
                throw Failures.instance().failure(
                    info,
                    ShouldBeEqual.shouldBeEqual( actualOtherContent, expectedOtherContent, info.representation() )
                );
            }

            if ( !expectedFormatterContent.equals( actualFormatterContent ) ) {
                throw Failures.instance().failure(
                    info,
                    ShouldBeEqual.shouldBeEqual(
                        actualFormatterContent,
                        expectedFormatterContent,
                        info.representation()
                    )
                );
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
     * Checks if the line should be ignored. The line is ignored if it is a deletion type for the license header
     * or if it is a change delta for the date/comments part of a {@code @Generated} annotation.
     *
     * @param line that needs to be checked
     * @return {@code true} if this line should be ignored, {@code false} otherwise
     */
    private boolean isIgnorableLine(String line) {
        String trimmedLine = line.trim();

        // Ignore blank lines and lines that are part of a standard Javadoc/license header
        if ( trimmedLine.isEmpty() || trimmedLine.startsWith( "/*" ) || trimmedLine.startsWith( "*" ) ) {
            return false;
        }
        return !LICENSE_PATTERN.matcher( line ).matches() && !GENERATED_DATE_PATTERN.matcher( line ).matches() &&
            !GENERATED_COMMENTS_PATTERN.matcher( line ).matches() &&
            !IMPORT_GENERATED_PATTERN.matcher( line ).matches();
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
