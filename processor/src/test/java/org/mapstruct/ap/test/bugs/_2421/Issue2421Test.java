package org.mapstruct.ap.test.bugs._2421;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2421")
@WithClasses({
    Company.class,
    CompanyDto.class,
    CompanyMapper.class,
    CompanyType.class
})
public class Issue2421Test {

    @ProcessorTest
    public void shouldGenerateValidCode() {
        Company entity = new Company();
        entity.setName( "Test" );
        entity.setCompanyType( CompanyType.inspection );

        CompanyDto dto = CompanyMapper.INSTANCE.toDto( entity );

        assertThat( dto.getName() ).isEqualTo( "Test" );
        assertThat( dto.getCompanyType() ).isEqualTo( "inspection" );
        assertThat( dto.getCompanyTypeLatinTitle() ).isEqualTo( "inspectionem" );

        entity = CompanyMapper.INSTANCE.toEntity( dto );

        assertThat( entity.getName() ).isEqualTo( "Test" );
        assertThat( entity.getCompanyType() ).isEqualTo( CompanyType.inspection );
    }
}
