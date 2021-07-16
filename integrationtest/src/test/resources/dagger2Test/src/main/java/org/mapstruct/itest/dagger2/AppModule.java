/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.dagger2;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    public static SourceTargetMapper sourceTargetMapper(SourceTargetMapperImpl impl) {
        return impl;
    }

    @Provides
    @Named("org.mapstruct.itest.dagger2.DecoratedSourceTargetMapperImpl_")
    public static DecoratedSourceTargetMapper decoratedSourceTargetMapper_(
        DecoratedSourceTargetMapperImpl_ impl) {
        return impl;
    }

    @Provides
    public static DecoratedSourceTargetMapper decoratedSourceTargetMapper(DecoratedSourceTargetMapperImpl impl) {
        return impl;
    }

    @Provides
    @Named("org.mapstruct.itest.dagger2.SecondDecoratedSourceTargetMapperImpl_")
    public static SecondDecoratedSourceTargetMapper secondDecoratedSourceTargetMapper_(SecondDecoratedSourceTargetMapperImpl_ impl) {
        return impl;
    }

    @Provides
    public static SecondDecoratedSourceTargetMapper secondDecoratedSourceTargetMapper(SecondDecoratedSourceTargetMapperImpl impl) {
        return impl;
    }

}
