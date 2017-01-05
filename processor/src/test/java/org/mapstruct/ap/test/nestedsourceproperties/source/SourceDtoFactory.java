/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.nestedsourceproperties.source;

/**
 *
 * @author Sjaak Derksen
 */
public class SourceDtoFactory {

    private SourceDtoFactory() { }

    private static boolean createArtistCalled = false;
    private static boolean createStudioCalled = false;
    private static boolean createLabelCalled = false;
    private static boolean createSongCalled = false;

    public static Artist createArtist() {
        createArtistCalled = true;
        return new Artist();
    }

    public static Studio createStudio() {
        createStudioCalled = true;
        return new Studio();
    }

    public static Label createLabel() {
        createLabelCalled = true;
        return new Label();
    }

    public static Song createSong() {
        createSongCalled = true;
        return new Song();
    }

    public static boolean isCreateArtistCalled() {
        return createArtistCalled;
    }

    public static boolean isCreateStudioCalled() {
        return createStudioCalled;
    }

    public static boolean isCreateLabelCalled() {
        return createLabelCalled;
    }

    public static boolean isCreateSongCalled() {
        return createSongCalled;
    }

}
