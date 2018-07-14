/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
