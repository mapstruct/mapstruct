/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper
public abstract class Issue1460Mapper {

    public static final Issue1460Mapper INSTANCE = Mappers.getMapper( Issue1460Mapper.class );

    public abstract Target map(Source source);

    public abstract String forceUsageOfIssue1460Enum(Issue1460Enum source);

    public abstract String forceUsageOfLocale(Locale source);

    public abstract String forceUsageOfLocalDate(LocalDate source);

    public abstract String forceUsageOfDateTime(DateTime source);

    public static class Issue1460Enum {
    }

    public static class Locale {
    }

    public static class LocalDate {
    }

    public static class DateTime {
    }
}
