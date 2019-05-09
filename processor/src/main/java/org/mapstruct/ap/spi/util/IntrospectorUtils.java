/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi.util;

/**
 * Utilities for tools to learn about the properties, events, and methods supported by a target Java Bean.
 * It is mainly needed to avoid using {@link java.beans.Introspector} class which is part of java.desktop module
 * from java 9, thus avoiding pulling a module ( of around 10 MB ) for using a single class.
 *
 * @author Saheb Preet Singh
 */
public class IntrospectorUtils {

    private static final String METHOD_NAME_PATTERN = "^%s[A-Z].*";
    private static final String GETTER_METHOD_NAME_PATTERN = String.format( METHOD_NAME_PATTERN, "get" );
    private static final String BOOLEAN_GETTER_METHOD_NAME_PATTERN = String.format( METHOD_NAME_PATTERN, "is" );
    private static final String SETTER_METHOD_NAME_PATTERN = String.format( METHOD_NAME_PATTERN, "set" );
    private static final String WITHER_METHOD_NAME_PATTERN = String.format( METHOD_NAME_PATTERN, "with" );
    private static final String ADDER_METHOD_NAME_PATTERN = String.format( METHOD_NAME_PATTERN, "add" );
    private static final String PRESENCE_CHECKER_METHOD_NAME_PATTERN = String.format( METHOD_NAME_PATTERN, "has" );

    private IntrospectorUtils() {
    }

    /**
     * Utility method to take a string and convert it to normal Java variable
     * name capitalization.  This normally means converting the first
     * character from upper case to lower case, but in the (unusual) special
     * case when there is more than one character and both the first and
     * second characters are upper case, we leave it alone.
     * <p>
     * Thus "FooBah" becomes "fooBah" and "X" becomes "x", but "URL" stays
     * as "URL".
     *
     * @param name The string to be decapitalized.
     *
     * @return The decapitalized version of the string.
     */
    public static String decapitalize(String name) {
        if ( name == null || name.length() == 0 ) {
            return name;
        }
        if ( name.length() > 1 && Character.isUpperCase( name.charAt( 1 ) ) &&
            Character.isUpperCase( name.charAt( 0 ) ) ) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase( chars[0] );
        return new String( chars );
    }

    public static boolean isGetter(final String methodName) {
        return methodName.matches( GETTER_METHOD_NAME_PATTERN );
    }

    public static boolean isBooleanGetter(final String methodName) {
        return methodName.matches( BOOLEAN_GETTER_METHOD_NAME_PATTERN );
    }

    public static boolean isSetter(final String methodName) {
        return methodName.matches( SETTER_METHOD_NAME_PATTERN );
    }

    public static boolean isWither(final String methodName) {
        return methodName.matches( WITHER_METHOD_NAME_PATTERN );
    }

    public static boolean isAdder(final String methodName) {
        return methodName.matches( ADDER_METHOD_NAME_PATTERN );
    }

    public static boolean isPresenceChecker(final String methodName) {
        return methodName.matches( PRESENCE_CHECKER_METHOD_NAME_PATTERN );
    }
}
