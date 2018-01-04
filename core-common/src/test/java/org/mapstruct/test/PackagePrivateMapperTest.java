package org.mapstruct.test;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PackagePrivateMapperTest {

    @Test
    public void shouldReturnPackagePrivateImplementationInstance() {
        assertThat(PackagePrivateMapper.INSTANCE).isNotNull();
    }


}