/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import org.mapstruct.testutil.runner.CompilerLauncherDiscoveryListener;

/**
 * @author Ben Zegveld
 */
public class MapStructCompilerLauncherDiscoveryListener extends CompilerLauncherDiscoveryListener {

    public MapStructCompilerLauncherDiscoveryListener() {
        super( "org.mapstruct.ap.test." );
    }
}
