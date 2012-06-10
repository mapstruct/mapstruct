/**
 *  Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package de.moapa.maple.ap.writer;

import java.io.BufferedWriter;
import javax.tools.JavaFileObject;

import de.moapa.maple.ap.model.Mapper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class NativeModelWriter implements ModelWriter {

	private final static String TEMPLATE_NAME = "native-mapper-implementation.ftl";

	private static final Configuration configuration;

	static {

		configuration = new Configuration();
		configuration.setClassForTemplateLoading( NativeModelWriter.class, "/" );
		configuration.setObjectWrapper( new DefaultObjectWrapper() );

	}

	@Override
	public void writeModel(JavaFileObject sourceFile, Mapper model) {

		try {
			BufferedWriter writer = new BufferedWriter( sourceFile.openWriter() );

			Template template = configuration.getTemplate( TEMPLATE_NAME );
			template.process( model, writer );
			writer.flush();
			writer.close();
		}
		catch ( RuntimeException e ) {
			throw e;
		}
		catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}
}
