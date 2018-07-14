/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.mapstruct.Mapper;

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
            List<ClassLoader> classLoaders = new ArrayList<ClassLoader>( 3 );
            classLoaders.add( clazz.getClassLoader() );

            if ( Thread.currentThread().getContextClassLoader() != null ) {
                classLoaders.add( Thread.currentThread().getContextClassLoader() );
            }

            classLoaders.add( Mappers.class.getClassLoader() );

            return getMapper( clazz, classLoaders );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( e );
        }
        catch ( NoSuchMethodException e) {
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

    private static <T> T doGetMapper(Class<T> clazz, ClassLoader classLoader) throws NoSuchMethodException {
        try {
            @SuppressWarnings( "unchecked" )
            Class<T> implementation = (Class<T>) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX );
            Constructor<T> constructor = implementation.getDeclaredConstructor();
            constructor.setAccessible( true );
            return constructor.newInstance();
        }
        catch (ClassNotFoundException e) {
            ServiceLoader<T> loader = ServiceLoader.load( clazz, classLoader );

            if ( loader != null ) {
                for ( T mapper : loader ) {
                    if ( mapper != null ) {
                        return mapper;
                    }
                }
            }

            return null;
        }
        catch (InstantiationException e) {
            throw new RuntimeException( e );
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException( e );
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException( e );
        }
    }
}
