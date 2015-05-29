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
package org.mapstruct.ap.internal.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class taking care of Noun manipulation
 *
 * @author Sjaak Derksen
 */
public class Nouns {

    private Nouns() {
    }

    private static final List<ReplaceRule> SINGULAR_RULES = Arrays.asList(
        new ReplaceRule( "(equipment|information|rice|money|species|series|fish|sheep)$", "$1" ),
        new ReplaceRule( "(f)eet$", "$1oot" ),
        new ReplaceRule( "(t)eeth$", "$1ooth" ),
        new ReplaceRule( "(g)eese$", "$1oose" ),
        new ReplaceRule( "(s)tadiums$", "$1tadium" ),
        new ReplaceRule( "(m)oves$", "$1ove" ),
        new ReplaceRule( "(s)exes$", "$1ex" ),
        new ReplaceRule( "(c)hildren$", "$1hild" ),
        new ReplaceRule( "(m)en$", "$1an" ),
        new ReplaceRule( "(p)eople$", "$1erson" ),
        new ReplaceRule( "(quiz)zes$", "$1" ),
        new ReplaceRule( "(matr)ices$", "$1ix" ),
        new ReplaceRule( "(vert|ind)ices$", "$1ex" ),
        new ReplaceRule( "^(ox)en", "$1" ),
        new ReplaceRule( "(alias|status)$", "$1" ), // already singular, but ends in 's'
        new ReplaceRule( "(alias|status)es$", "$1" ),
        new ReplaceRule( "(octop|vir)us$", "$1us" ), // already singular, but ends in 's'
        new ReplaceRule( "(octop|vir)i$", "$1us" ),
        new ReplaceRule( "(cris|ax|test)es$", "$1is" ),
        new ReplaceRule( "(cris|ax|test)is$", "$1is" ), // already singular, but ends in 's'
        new ReplaceRule( "(shoe)s$", "$1" ),
        new ReplaceRule( "(o)es$", "$1" ),
        new ReplaceRule( "(bus)es$", "$1" ),
        new ReplaceRule( "([m|l])ice$", "$1ouse" ),
        new ReplaceRule( "(x|ch|ss|sh)es$", "$1" ),
        new ReplaceRule( "(m)ovies$", "$1ovie" ),
        new ReplaceRule( "(s)eries$", "$1eries" ),
        new ReplaceRule( "([^aeiouy]|qu)ies$", "$1y" ),
        new ReplaceRule( "([lr])ves$", "$1f" ),
        new ReplaceRule( "(tive)s$", "$1" ),
        new ReplaceRule( "(hive)s$", "$1" ),
        new ReplaceRule( "([^f])ves$", "$1fe" ),
        new ReplaceRule( "(^analy)sis$", "$1sis" ), // already singular, but ends in 's'
        new ReplaceRule( "(^analy)ses$", "$1sis" ),
        new ReplaceRule( "((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$", "$1$2sis" ),
        new ReplaceRule( "([ti])a$", "$1um" ),
        new ReplaceRule( "(n)ews$", "$1ews" ),
        new ReplaceRule( "(s|si|u)s$", "$1s" ), // '-us' and '-ss' are already singular
        new ReplaceRule( "s$", "" )
    );

    /**
     * Replacement rules based on the routine applied by the <a href="http://www.eclipse.org/webtools/dali/">Dali</a>
     * project. Applied as a fallback if the other rules didn't yield a match.
     */
    private static final List<ReplaceRule> SINGULAR_DALI_RULES = Arrays.asList(
        new ReplaceRule( "(us|ss)$", "$1" ),
        new ReplaceRule( "(ch|s)es$", "$1" ),
        new ReplaceRule( "([^aeiouy])ies$", "$1y" )
    );

    /**
     * Converts given pluralized noun into the singular form. If no singular form could be determined, the given word
     * itself is returned.
     *
     * @param plural plural word
     * @return singular form, if available
     */
    public static String singularize(String plural) {
        for ( ReplaceRule replaceRule : SINGULAR_RULES ) {
            String match = replaceRule.apply( plural );
            if ( match != null ) {
                return match;
            }
        }

        for ( ReplaceRule replaceRule : SINGULAR_DALI_RULES ) {
            String match = replaceRule.apply( plural );
            if ( match != null ) {
                return match;
            }
        }

        return plural;
    }

    private static final class ReplaceRule {

        private final String regexp;
        private final String replacement;
        private final Pattern pattern;

        private ReplaceRule(String regexp, String replacement) {
            this.regexp = regexp;
            this.replacement = replacement;
            this.pattern = Pattern.compile( this.regexp, Pattern.CASE_INSENSITIVE );
        }

        private String apply(String input) {
            String result = null;
            Matcher matcher = this.pattern.matcher( input );
            if ( matcher.find() ) {
                result = matcher.replaceAll( this.replacement );
            }
            return result;
        }

        @Override
        public String toString() {
            return "'" + regexp + "' -> '" + replacement;
        }
    }
}
