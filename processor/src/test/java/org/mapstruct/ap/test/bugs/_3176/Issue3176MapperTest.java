package org.mapstruct.ap.test.bugs._3176;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithClasses({ Source.class, TargetWithBuilder.class, Issue3176Mapper.class })
@IssueKey("3176")
@Disabled
class Issue3176MapperTest {

	@RegisterExtension
	GeneratedSource generated = new GeneratedSource();

	@ProcessorTest
	void AfterMappingForOriginalSourceWithBuilder() {
		generated.forMapper(Issue3176Mapper.class).content()
			.contains("handleOriginalAfterMapping");
	}

}
