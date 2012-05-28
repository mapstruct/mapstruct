<#--

     Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

-->
package ${packageName};

import org.dozer.DozerConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import de.moapa.maple.converter.Converter;

import static org.dozer.loader.api.FieldsMappingOptions.*;

public class ${implementationType} implements ${interfaceType} {

    private final DozerBeanMapper mapper;

    public ${implementationType}() {
        mapper = new DozerBeanMapper();

        BeanMappingBuilder builder = null;

        <#list mapperMethods as oneMethod>
        <#if oneMethod.bindings?has_content>
        builder = new BeanMappingBuilder() {
            protected void configure() {
                mapping( ${oneMethod.parameter.type.name}.class, ${oneMethod.returnType.name}.class )
                    <#list oneMethod.bindings as oneBinding>
                    .fields( "${oneBinding.sourceProperty}", "${oneBinding.targetProperty}"<#if oneBinding.converterType??>, customConverter( ${oneBinding.converterType.name}DozerAdapter.class )</#if> )
                    </#list>
                ;
            }
        };
        mapper.addMapping( builder );
        </#if>

        </#list>
    }

    <#list mapperMethods as oneMethod>
    public ${oneMethod.returnType.name} ${oneMethod.name}(${oneMethod.parameter.type.name} ${oneMethod.parameter.name}) {
        return mapper.map(${oneMethod.parameter.name}, ${oneMethod.returnType.name}.class);
    }
    </#list>

    <#list converters as oneConverter>
    public static class ${oneConverter.converterType.name}DozerAdapter extends DozerConverter<${oneConverter.sourceType.name}, ${oneConverter.targetType.name}> {

        private final Converter<${oneConverter.sourceType.name}, ${oneConverter.targetType.name}> converter = new ${oneConverter.converterType.name}();

        public ${oneConverter.converterType.name}DozerAdapter() {
            super(${oneConverter.sourceType.name}.class, ${oneConverter.targetType.name}.class);
        }

        @Override
        public String convertTo(${oneConverter.sourceType.name} source, ${oneConverter.targetType.name} destination) {
            return converter.from(source);
        }

        @Override
        public ${oneConverter.sourceType.name} convertFrom(${oneConverter.targetType.name} source, ${oneConverter.sourceType.name} destination) {
            return converter.to(source);
        }
    }
    </#list>
}
