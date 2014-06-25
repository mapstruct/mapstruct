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
package org.mapstruct.ap.test.source.expressions.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, TimeAndFormat.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class JavaExpressionTest {

    @Test
    public void testJavaExpressionInsertion() throws ParseException {
        Source source = new Source();
        String format = "dd-MM-yyyy,hh:mm:ss";
        Date time = getTime( format, "09-01-2014,01:35:03" );

        source.setFormat( format );
        source.setTime( time );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getTimeAndFormat().getTime() ).isEqualTo( time );
        assertThat( target.getTimeAndFormat().getFormat() ).isEqualTo( format );
    }

    private Date getTime(String format, String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
        Date result = dateFormat.parse( date );
        return result;
    }
}
