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
package org.mapstruct.ap.internal.writer;

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
 *
 * <p>
 * The state pattern is line oriented. It starts by writing text. Indentation is increased if a brace '('or
 * brace '{' is encountered in the code to be generated and written out in state: IN_TEXT_START_OF_LINE. Whenever
 * a line end occurs (PC or Linux style) the amount of enters is checked and at max set to 2.
 *
 * Whenever a string definition is encountered in the code that should be generated, increasing the indentation is
 * stopped (so `{` and '(' are ignored) until the end of the string is encountered ('"'). To avoid writing a new
 * indentation, the state then returns to IN_TEXT.
 *
 * <p>
 * This is a very basic implementation which does not take into account comments, escaping etc.
 *
 * @author Gunnar Morling
 */
class IndentationCorrectingWriter extends Writer {

    /**
     * Set to true to enable output of written characters on the console.
     */
    private static final boolean DEBUG = false;
    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    private static final boolean IS_WINDOWS = System.getProperty( "os.name" ).startsWith( "Windows" );

    private State currentState = State.START_OF_LINE;
    private final StateContext context;

    IndentationCorrectingWriter(Writer out) {
        super( out );
        this.context = new StateContext( out );
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        context.reset( cbuf, off );

        for ( int i = off; i < len; i++ ) {
            char c = cbuf[i];

            State newState = currentState.handleCharacter( c, context );

            if ( newState != currentState ) {
                currentState.onExit( context, newState );
                newState.onEntry( context );
                currentState = newState;
            }

            context.currentIndex++;
        }

        currentState.onBufferFinished( context );
    }

    @Override
    public void flush() throws IOException {
        context.writer.flush();
    }

    @Override
    public void close() throws IOException {
        currentState.onExit( context, null );
        context.writer.close();
    }

    private static boolean isWindows() {
        return IS_WINDOWS;
    }

    private static char[] getIndentation(int indentationLevel) {
        char[] indentation = new char[indentationLevel * 4];
        Arrays.fill( indentation, ' ' );
        return indentation;
    }

    /**
     * A state of parsing a given character buffer.
     */
    private enum State {

        /**
         * Within any text, before encountering a String definition.
         */
        START_OF_LINE {
            @Override
            State doHandleCharacter(char c, StateContext context) {
                switch ( c ) {
                    case '{':
                    case '(':
                        context.indentationLevel++;
                        return IN_TEXT;
                    case '}':
                    case ')':
                        context.indentationLevel--;
                        return IN_TEXT;
                    case '\"':
                        return IN_STRING;
                    case '\r':
                        return isWindows() ? IN_LINE_BREAK : AFTER_LINE_BREAK;
                    case '\n':
                        return AFTER_LINE_BREAK;
                    default:
                        return IN_TEXT;
                }
            }

            /**
             * Writes out leading whitespace as per the current indentation level.
             */
            @Override
            void doOnEntry(StateContext context) throws IOException {
                context.writer.write( getIndentation( context.indentationLevel ) );

                if ( DEBUG ) {
                    System.out.print( new String( getIndentation( context.indentationLevel ) ).replace( " ", "_" ) );
                }
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onExit(StateContext context, State nextState) throws IOException {
                flush( context );
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onBufferFinished(StateContext context) throws IOException {
                flush( context );
            }

        },

        /**
         * Within any text, but after a String (" ").
         */
        IN_TEXT {
            @Override
            State doHandleCharacter(char c, StateContext context) {
                switch ( c ) {
                    case '{':
                    case '(':
                        context.indentationLevel++;
                        return IN_TEXT;
                    case '}':
                    case ')':
                        context.indentationLevel--;
                        return IN_TEXT;
                    case '\"':
                        return IN_STRING;
                    case '\r':
                        return isWindows() ? IN_LINE_BREAK : AFTER_LINE_BREAK;
                    case '\n':
                        return AFTER_LINE_BREAK;
                    default:
                        return IN_TEXT;
                }
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onExit(StateContext context, State nextState) throws IOException {
                flush( context );
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onBufferFinished(StateContext context) throws IOException {
                flush( context );
            }

        },

        /**
         * In a String definition, Between un-escaped quotes " "
         */
        IN_STRING {

            @Override
            State doHandleCharacter(char c, StateContext context) {
                switch ( c ) {
                    case '\"':
                        return IN_TEXT;
                    case '\\':
                        return IN_STRING_ESCAPED_CHAR;
                    default:
                        return IN_STRING;
                }
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onExit(StateContext context, State nextState) throws IOException {
                flush( context );
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onBufferFinished(StateContext context) throws IOException {
                flush( context );
            }

        },

        /**
         * In a String, character following an escape character '\', should be ignored, can also be '"' that
         * should be ignored.
         */
        IN_STRING_ESCAPED_CHAR {

            @Override
            State doHandleCharacter(char c, StateContext context) {
                // ignore escaped character
                return IN_STRING;
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onExit(StateContext context, State nextState) throws IOException {
                flush( context );
            }

            /**
             * Writes out the current text.
             */
            @Override
            void onBufferFinished(StateContext context) throws IOException {
                flush( context );
            }

        },

        /**
         * Between \r and \n of a Windows line-break.
         */
        IN_LINE_BREAK {
            @Override
            State doHandleCharacter(char c, StateContext context) {
                if ( c == '\n' ) {
                    return AFTER_LINE_BREAK;
                }
                else {
                    throw new IllegalArgumentException( "Unexpected character: " + c );
                }
            }
        },

        /**
         * Directly after a line-break, or within leading whitespace following to a line-break.
         */
        AFTER_LINE_BREAK {
            @Override
            State doHandleCharacter(char c, StateContext context) {

                switch ( c ) {
                    case '{':
                    case '(':
                        context.indentationLevel++;
                        return START_OF_LINE;
                    case '}':
                        if ( context.consecutiveLineBreaks > 0 ) {
                            context.consecutiveLineBreaks = 0; // remove previous blank lines
                        }
                    case ')':
                        context.indentationLevel--;
                        return START_OF_LINE;
                    case '\r':
                        return isWindows() ? IN_LINE_BREAK : AFTER_LINE_BREAK;
                    case ' ':
                        return AFTER_LINE_BREAK;
                    case '\n':
                        context.consecutiveLineBreaks++;
                        return AFTER_LINE_BREAK;
                    default:
                        return START_OF_LINE;
                }
            }

            /**
             * Writes out the current line-breaks, avoiding more than one consecutive empty line
             */
            @Override
            void onExit(StateContext context, State nextState) throws IOException {
                context.consecutiveLineBreaks++;
                if ( nextState != IN_LINE_BREAK ) {
                    int lineBreaks = Math.min( context.consecutiveLineBreaks, 2 );

                    for ( int i = 0; i < lineBreaks; i++ ) {
                        context.writer.append( LINE_SEPARATOR );

                        if ( DEBUG ) {
                            System.out.print( "\\n" + LINE_SEPARATOR );
                        }
                    }

                    context.consecutiveLineBreaks = 0;
                }
            }
        };

        final State handleCharacter(char c, StateContext context) throws IOException {


            return doHandleCharacter( c, context );
        }

        abstract State doHandleCharacter(char c, StateContext context) throws IOException;

        final void onEntry(StateContext context) throws IOException {
            context.lastStateChange = context.currentIndex;
            doOnEntry( context );
        }

        void doOnEntry(StateContext context) throws IOException {
        }

        void onExit(StateContext context, State nextState) throws IOException {
        }

        void onBufferFinished(StateContext context) throws IOException {
        }

        protected void flush(StateContext context) throws IOException {
            if ( null != context.characters && context.currentIndex - context.lastStateChange > 0 ) {
                context.writer.write(
                    context.characters,
                    context.lastStateChange,
                    context.currentIndex - context.lastStateChange
                );

                if ( DEBUG ) {
                    System.out.print(
                        new String(
                            java.util.Arrays.copyOfRange(
                                context.characters,
                                context.lastStateChange,
                                context.currentIndex
                            )
                        )
                    );
                }
            }
        }
    }

    /**
     * Keeps the current context of parsing the given character buffer.
     */
    private static class StateContext {
        final Writer writer;

        char[] characters;

        /**
         * The position at which when the current state was entered.
         */
        int lastStateChange;

        /**
         * The current position within the buffer.
         */
        int currentIndex;

        /**
         * Keeps track of the current indentation level, as implied by brace characters.
         */
        int indentationLevel;

        /**
         * The number of consecutive line-breaks when within {@link State#AFTER_LINE_BREAK}.
         */
        int consecutiveLineBreaks;

        StateContext(Writer writer) {
            this.writer = writer;
        }

        void reset(char[] characters, int off) {
            this.characters = characters;
            this.lastStateChange = off;
            this.currentIndex = 0;
        }
    }
}
