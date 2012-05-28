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
package de.moapa.maple.ap.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import de.moapa.maple.ap.MappingProcessor;
import de.moapa.maple.ap.test.model.Car;
import de.moapa.maple.ap.test.model.CarDto;
import de.moapa.maple.ap.test.model.CarMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CarMapperTest {

	private DiagnosticCollector<JavaFileObject> diagnostics;
	private JavaCompiler compiler;

	private String sourceDir;
	private String classOutputDir;
	private String sourceOutputDir;
	private List<File> classPath;

	@BeforeClass
	public void setup() throws Exception {

		compiler = ToolProvider.getSystemJavaCompiler();

		String basePath = getBasePath();

		sourceDir = basePath + "/src/test/java";
		classOutputDir = basePath + "/target/compilation-tests/classes";
		sourceOutputDir = basePath + "/target/compilation-tests/generated-sources/mapping";

		String testDependenciesDir = basePath + "/target/test-dependencies/";
		classPath = Arrays.asList(
				new File( testDependenciesDir, "maple.jar" ),
				new File( testDependenciesDir, "dozer.jar" ),
				new File( testDependenciesDir, "slf4j-api.jar" ),
				new File( testDependenciesDir, "slf4j-jdk14.jar" )
		);

		createOutputDirs();

		Thread.currentThread().setContextClassLoader(
				new URLClassLoader(
						new URL[] { new File( classOutputDir ).toURI().toURL() },
						Thread.currentThread().getContextClassLoader()
				)
		);
	}

	@BeforeMethod
	public void generateMapperImplementation() {

		diagnostics = new DiagnosticCollector<JavaFileObject>();
		File[] sourceFiles = getSourceFiles( Car.class, CarDto.class, CarMapper.class );

		boolean compilationSuccessful = compile( diagnostics, sourceFiles );

		assertThat( compilationSuccessful ).describedAs( "Compilation failed: " + diagnostics.getDiagnostics() )
				.isTrue();
	}

	@Test
	public void shouldProvideMapperInstance() throws Exception {

		assertThat( CarMapper.INSTANCE ).isNotNull();
	}

	@Test
	public void shouldMapAttributeByName() {

		//given
		Car car = new Car( "Morris", 2 );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getMake() ).isEqualTo( car.getMake() );
	}

	@Test
	public void shouldMapAttributeWithCustomMapping() {

		//given
		Car car = new Car( "Morris", 2 );

		//when
		CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

		//then
		assertThat( carDto ).isNotNull();
		assertThat( carDto.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
	}

	@Test
	public void shouldConsiderCustomMappingForReverseMapping() {

		//given
		CarDto carDto = new CarDto( "Morris", 2 );

		//when
		Car car = CarMapper.INSTANCE.carDtoToCar( carDto );

		//then
		assertThat( car ).isNotNull();
		assertThat( car.getNumberOfSeats() ).isEqualTo( carDto.getSeatCount() );
	}

	private File[] getSourceFiles(Class<?>... clazz) {

		File[] sourceFiles = new File[clazz.length];

		for ( int i = 0; i < clazz.length; i++ ) {

			sourceFiles[i] = new File(
					sourceDir +
							File.separator +
							clazz[i].getName().replace( ".", File.separator ) +
							".java"
			);
		}


		return sourceFiles;
	}

	public boolean compile(DiagnosticCollector<JavaFileObject> diagnostics, File... sourceFiles) {

		StandardJavaFileManager fileManager = compiler.getStandardFileManager( null, null, null );

		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects( sourceFiles );

		try {
			fileManager.setLocation( StandardLocation.CLASS_PATH, classPath );
			fileManager.setLocation( StandardLocation.CLASS_OUTPUT, Arrays.asList( new File( classOutputDir ) ) );
			fileManager.setLocation( StandardLocation.SOURCE_OUTPUT, Arrays.asList( new File( sourceOutputDir ) ) );
		}
		catch ( IOException e ) {
			throw new RuntimeException( e );
		}

		CompilationTask task = compiler.getTask(
				null,
				fileManager,
				diagnostics,
				Collections.<String>emptyList(),
				null,
				compilationUnits
		);
		task.setProcessors( Arrays.asList( new MappingProcessor() ) );

		return task.call();
	}

	private String getBasePath() {

		try {
			return new File( "." ).getCanonicalPath();
		}
		catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}

	private void createOutputDirs() {

		File directory = new File( classOutputDir );
		deleteDirectory( directory );
		directory.mkdirs();

		directory = new File( sourceOutputDir );
		deleteDirectory( directory );
		directory.mkdirs();
	}

	private void deleteDirectory(File path) {
		if ( path.exists() ) {
			File[] files = path.listFiles();
			for ( int i = 0; i < files.length; i++ ) {
				if ( files[i].isDirectory() ) {
					deleteDirectory( files[i] );
				}
				else {
					files[i].delete();
				}
			}
		}
		path.delete();
	}
}
