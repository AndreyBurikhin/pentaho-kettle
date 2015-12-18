package org.pentaho.di.core.injection.bean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.exception.KettleException;

/**
 * Engine for get/set metadata injection properties from bean.
 */
public class BeanInjector {
  private final BeanInjectionInfo info;

  public BeanInjector( BeanInjectionInfo info ) {
    this.info = info;
  }

  public Object getProperty( Object root, String propName ) throws Exception {
    List<Integer> extractedIndexes = new ArrayList<>();

    BeanInjectionInfo.Property prop = info.getProperties().get( propName );
    if ( prop == null ) {
      throw new RuntimeException( "Property not found" );
    }

    Object obj = root;
    for ( int i = 1, arrIndex = 0; i < prop.path.size(); i++ ) {
      BeanLevelInfo s = prop.path.get( i );
      obj = s.field.get( obj );
      if ( obj == null ) {
        return null; // some value in path is null - return empty
      }
      if ( s.array ) {
        int index = extractedIndexes.get( arrIndex++ );
        if ( Array.getLength( obj ) <= index ) {
          return null;
        }
        obj = Array.get( obj, index );
        if ( obj == null ) {
          return null; // element is empty
        }
      }
    }
    return obj;
  }

  public void setProperty( Object root, String propName, List<Object> value ) throws KettleException {
    BeanInjectionInfo.Property prop = info.getProperties().get( propName );
    if ( prop == null ) {
      throw new KettleException( "Property '" + propName + "' not found for injection to " + root.getClass() );
    }

    if ( prop.pathArraysCount == 0 ) {
      // no arrays in path
      try {
        setProperty( root, prop, 0, value.get( 0 ) );
      } catch ( Exception ex ) {
        throw new KettleException( "Error inject property '" + propName + "' into " + root.getClass(), ex );
      }
    } else if ( prop.pathArraysCount == 1 ) {
      // one array in path
      try {
        for ( int i = 0; i < value.size(); i++ ) {
          setProperty( root, prop, i, value.get( i ) );
        }
      } catch ( Exception ex ) {
        throw new KettleException( "Error inject property '" + propName + "' into " + root.getClass(), ex );
      }
    } else {
      if ( prop.pathArraysCount > 1 ) {
        throw new KettleException( "Property '" + propName + "' has more than one array in path for injection to "
            + root.getClass() );
      }
    }
  }

  private void setProperty( Object root, BeanInjectionInfo.Property prop, int index, Object value ) throws Exception {
    Object obj = root;
    for ( int i = 1; i < prop.path.size(); i++ ) {
      BeanLevelInfo s = prop.path.get( i );
      if ( i < prop.path.size() - 1 ) {
        // get path
        if ( s.array ) {
          // array
          Object existArray = s.field.get( obj );
          if ( existArray == null ) {
            existArray = Array.newInstance( s.leafClass, index + 1 );
            s.field.set( obj, existArray );
          }
          int existSize = Array.getLength( existArray );
          if ( existSize <= index ) {
            int newSize = index + 1;
            Object newSized = Array.newInstance( s.leafClass, newSize );
            System.arraycopy( existArray, 0, newSized, 0, existSize );
            existArray = newSized;
            s.field.set( obj, existArray );
          }
          Object next = Array.get( existArray, index ); // get specific element
          if ( next == null ) {
            next = s.leafClass.newInstance();
            Array.set( existArray, index, next );
          }
          obj = next;
        } else {
          // plain field
          Object next = s.field.get( obj );
          if ( next == null ) {
            next = s.leafClass.newInstance();
            s.field.set( obj, next );
          }
          obj = next;
        }
      } else {
        // set to latest field
        s.field.set( obj, value );
      }
    }
  }
}
