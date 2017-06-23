package org.mapstruct.ap.test.builder.abstractGenericTarget;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class AbstractTargetMapper {

    abstract ImmutableParentTargetImpl toImmutable(ParentSource parentSource);

    abstract MutableParentTargetImpl toMutable(ParentSource parentSource);

    abstract AbstractChildTarget toAbstract(ChildSource childSource);

    @ObjectFactory
    public ImmutableChildTargetImpl.Builder childTargetFactory() {
        return ImmutableChildTargetImpl.builder();
    }



//    @ObjectFactory
//    public MutableChildTargetImpl targetImplFactory() {
//        return new MutableChildTargetImpl();
//    }
//    ImmutableChildTargetImpl toChild(ChildSource child);



}
