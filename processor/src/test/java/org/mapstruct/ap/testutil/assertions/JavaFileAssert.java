/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.testutil.assertions;

import java.io.File;
import java.io.IOException;

import org.fest.assertions.Assertions;
import org.fest.assertions.FileAssert;
import org.fest.assertions.StringAssert;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * Allows to perform assertions on .java source files.
 *
 * @author Andreas Gudian
 */
public class JavaFileAssert extends FileAssert {
    /**
     * @param actual the actual file
     */
    public JavaFileAssert(File actual) {
        super( actual );
    }

    /**
     * @return assertion on the file content
     */
    public StringAssert content() {
        exists();
        isFile();

        try {
            return Assertions.assertThat( Files.toString( actual, Charsets.UTF_8 ) );
        }
        catch ( IOException e ) {
            failIfCustomMessageIsSet( e );
        }
        return null;
    }

    /**
     * Verifies that the specified class is imported in this Java file
     *
     * @param importedClass the class expected to be imported in this Java file
     */
    public void containsImportFor(Class<?> importedClass) {
        content().contains( "import " + importedClass.getName() + ";" );
    }

    /**
     * Verifies that the specified class is not imported in this Java file
     *
     * @param importedClass the class expected not to be imported in this Java file
     */
    public void containsNoImportFor(Class<?> importedClass) {
        content().doesNotContain( "import " + importedClass.getName() + ";" );
    }
}
