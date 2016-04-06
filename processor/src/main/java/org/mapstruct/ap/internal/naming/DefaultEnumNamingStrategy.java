package org.mapstruct.ap.internal.naming;

import org.mapstruct.ap.spi.EnumNamingStrategy;

public class DefaultEnumNamingStrategy implements EnumNamingStrategy
{
    @Override
    public String toTarget(String source)
    {
        return source;
    }
}
