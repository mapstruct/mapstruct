package org.mapstruct.ap.test.bugs._3176;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class Issue3176Mapper {

    public abstract TargetWithBuilder toTarget(Source source);
	
	public static Issue3176Mapper INSTANCE = Mappers.getMapper(Issue3176Mapper.class);
	
	@AfterMapping
	public void handleOriginalAfterMapping(@MappingTarget TargetWithBuilder target) {
		target.setExample(target.getExample() + " after editing");
	}
	
	@AfterMapping
	public void handleBuilderAfterMapping(@MappingTarget TargetWithBuilder.Builder builder) {
		builder.example(builder.getExample() + " after builder editing");
	}
	
	@AfterMapping
	public void handleBuilderAfterMapping2(@MappingTarget TargetWithBuilder.Builder builder) {
		builder.example(builder.getExample() + " after builder editing");
	}
	
}
