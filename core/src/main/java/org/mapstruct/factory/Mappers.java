/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.factory;

import org.mapstruct.Mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Factory for obtaining mapper instances if no explicit component model such as CDI is configured via
 * {@link Mapper#componentModel()}.
 * <p>
 * Mapper implementation types are expected to have the same fully qualified name as their interface type, with the
 * suffix {@code Impl} appended. When using this factory, mapper types - and any mappers they use - are instantiated by
 * invoking their public no-args constructor.
 * <p>
 * By convention, a single instance of each mapper is retrieved from the factory and exposed on the mapper interface
 * type by declaring a member named {@code INSTANCE} like this:
 *
 * <pre>
 * &#064;Mapper
 * public interface CustomerMapper {
 *
 *     CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );
 *
 *     // mapping methods...
 * }
 * </pre>
 *
 * @author Gunnar Morling
 */
public class Mappers {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private Mappers() {
    }

    /**
     * Returns an instance of the given mapper type.
     *
     * @param clazz The type of the mapper to return.
     * @param <T> The type of the mapper to create.
     *
     * @return An instance of the given mapper type.
     */
    public static <T> T getMapper(Class<T> clazz) {
        try {
            List<ClassLoader> classLoaders = collectClassLoaders( clazz.getClassLoader() );

            return getMapper( clazz, classLoaders );
        }
        catch ( ClassNotFoundException | NoSuchMethodException e ) {
            throw new RuntimeException( e );
        }
    }

    private static <T> T getMapper(Class<T> mapperType, Iterable<ClassLoader> classLoaders)
            throws ClassNotFoundException, NoSuchMethodException {

        for ( ClassLoader classLoader : classLoaders ) {
            T mapper = doGetMapper( mapperType, classLoader );
            if ( mapper != null ) {
                return mapper;
            }
        }

        throw new ClassNotFoundException("Cannot find implementation for " + mapperType.getName() );
    }

    @SuppressWarnings("unchecked")
    private static <T> T doGetMapper(Class<T> clazz, ClassLoader classLoader) throws NoSuchMethodException {
        try {
            Class<T> implementation = (Class<T>) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX );
            Constructor<?>[] constructors = implementation.getConstructors();
            Constructor<T> constructor;
            if ( constructors.length == 0 ) {
                constructor = implementation.getDeclaredConstructor();
                constructor.setAccessible( true );
            }
            else {
                constructor = (Constructor<T>) constructors[0];
            }

            Object[] params = Arrays.stream(constructor.getParameterTypes())
                    .map( Mappers::getMapper )
                    .toArray( Object[]::new );
            return constructor.newInstance( params );
        }
        catch ( ClassNotFoundException e ) {
            return getMapperFromServiceLoader( clazz, classLoader );
        }
        catch ( InstantiationException | InvocationTargetException | IllegalAccessException e ) {
            throw new RuntimeException( e );
        }
    }

    /**
     * Returns the class of the implementation for the given mapper type.
     *
     * @param clazz The type of the mapper to return.
     * @param <T> The type of the mapper to create.
     *
     * @return A class of the implementation for the given mapper type.
     *
     * @since 1.3
     */
    public static <T> Class<? extends T> getMapperClass(Class<T> clazz) {
        try {
            List<ClassLoader> classLoaders = collectClassLoaders( clazz.getClassLoader() );

            return getMapperClass( clazz, classLoaders );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( e );
        }
    }

    private static <T> Class<? extends T> getMapperClass(Class<T> mapperType, Iterable<ClassLoader> classLoaders)
        throws ClassNotFoundException {

        for ( ClassLoader classLoader : classLoaders ) {
            Class<? extends T> mapperClass = doGetMapperClass( mapperType, classLoader );
            if ( mapperClass != null ) {
                return mapperClass;
            }
        }

        throw new ClassNotFoundException( "Cannot find implementation for " + mapperType.getName() );
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends T> doGetMapperClass(Class<T> clazz, ClassLoader classLoader) {
        try {
            return (Class<? extends T>) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX );
        }
        catch ( ClassNotFoundException e ) {
            T mapper = getMapperFromServiceLoader( clazz, classLoader );
            if ( mapper != null ) {
                return (Class<? extends T>) mapper.getClass();
            }

            return null;
        }
    }

    private static <T> T getMapperFromServiceLoader(Class<T> clazz, ClassLoader classLoader) {
        ServiceLoader<T> loader = ServiceLoader.load( clazz, classLoader );

        for ( T mapper : loader ) {
            if ( mapper != null ) {
                return mapper;
            }
        }

        return null;
    }

    private static List<ClassLoader> collectClassLoaders(ClassLoader classLoader) {
        List<ClassLoader> classLoaders = new ArrayList<>( 3 );
        classLoaders.add( classLoader );

        if ( Thread.currentThread().getContextClassLoader() != null ) {
            classLoaders.add( Thread.currentThread().getContextClassLoader() );
        }

        classLoaders.add( Mappers.class.getClassLoader() );

        return classLoaders;
    }
}
