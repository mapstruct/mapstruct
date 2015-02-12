/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/**
 * EXPERIMENTAL: A writer used to write processed templates which corrects line indentation based on the nesting level
 * as implied by (normal and curly) braces.
 * <p>
 * This writer discards any leading whitespace characters following to a line break character. When the first
 * non-whitespace character is written after a line break, the correct indentation characters are added, which is four
 * whitespace characters per indentation level.
 * <p>
 * This is a very basic implementation which does not take into account comments, escaping etc.
 *
 * @author Gunnar Morling
 */
class IndentationCorrectingWriter extends Writer {

    private static enum State {
        IN_TEXT, AFTER_LINE_BREAK, AFTER_BLANK_LINE;

        State handleCharacter(char c) {
            if ( this == IN_TEXT ) {
                switch ( c ) {
                    case '\n':
                        return AFTER_LINE_BREAK;
                    default:
                        return IN_TEXT;
                }
            }
            else if ( this == AFTER_LINE_BREAK ) {
                switch ( c ) {
                    case ' ':
                    case '\r':
                        return AFTER_LINE_BREAK;
                    case '\n':
                        return AFTER_BLANK_LINE;
                    default:
                        return IN_TEXT;
                }
            }
            else if ( this == AFTER_BLANK_LINE ) {
                switch ( c ) {
                    case ' ':
                    case '\r':
                    case '\n':
                        return AFTER_BLANK_LINE;
                    default:
                        return IN_TEXT;
                }
            }

            throw new IllegalStateException( "Unexpected state or character." );
        }
    }

    private final Writer delegate;
    private State state = State.IN_TEXT;

    /**
     * Keeps track of the current indentation level, as implied by brace characters.
     */
    private int indentationLevel = 0;

    IndentationCorrectingWriter(Writer out) {
        super( out );
        this.delegate = out;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        int start = off;
        int length = 0;

        for ( int i = off; i < len; i++ ) {
            char c = cbuf[i];
            if ( c == '{' || c == '(' ) {
                indentationLevel++;
            }
            else if ( c == '}' || c == ')' ) {
                indentationLevel--;
            }

            State newState = state.handleCharacter( c );
            length++;

            //write characters up to line-breaks
            if ( state == State.IN_TEXT && newState == State.AFTER_LINE_BREAK ) {
                delegate.write( cbuf, start, length );
            }
            //first non-whitespace character after a line break; write out the correct indentation, discarding any
            //original leading whitespace characters
            else if ( ( state == State.AFTER_LINE_BREAK || state == State.AFTER_BLANK_LINE )
                && newState == State.IN_TEXT ) {
                char[] indentation = getIndentation( indentationLevel );
                delegate.write( indentation, 0, indentation.length );
                start = i;
                length = 1;
            }
            //write out line-breaks following directly to other line breaks
            else if ( state == State.AFTER_LINE_BREAK && isLineBreak( c ) ) {
                    delegate.write( c );
            }
            //write out blank lines only if we don't create two successive blank lines
            else if ( state == State.AFTER_BLANK_LINE && newState != State.AFTER_BLANK_LINE && isLineBreak( c ) ) {
                delegate.write( c );
            }

            state = newState;
        }

        if ( state == State.IN_TEXT ) {
            delegate.write( cbuf, start, length );
        }
    }

    private static boolean isLineBreak(char c) {
        return c == '\n' || c == '\r';
    }

    private static char[] getIndentation(int indentationLevel) {
        char[] indentation = new char[indentationLevel * 4];
        Arrays.fill( indentation, ' ' );
        return indentation;
    }

    @Override
    public void flush() throws IOException {
        delegate.flush();
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}
