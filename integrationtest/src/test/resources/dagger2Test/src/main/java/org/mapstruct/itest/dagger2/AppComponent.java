/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.dagger2;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    SourceTargetMapper sourceTargetMapper();

    DecoratedSourceTargetMapper decoratedSourceTargetMapper();

    SecondDecoratedSourceTargetMapper secondDecoratedSourceTargetMapper();

}
