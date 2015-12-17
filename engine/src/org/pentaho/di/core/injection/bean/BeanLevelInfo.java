package org.pentaho.di.core.injection.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pentaho.di.core.injection.Injection;

/**
 * Storage for one step on the bean deep level.
 */
public class BeanLevelInfo {
  /** Parent step or null for root. */
  public BeanLevelInfo parent;
  /** Class for step from field or methods. */
  public Class<?> leafClass;
  /** Field of step, or null if bean has getter/setter. */
  public Field field;
  /** Flag for mark array. */
  public boolean array;
  /** Annotation name. TODO move outside. */
  public String annotationName;

  public void init( BeanInjectionInfo info ) {
    if ( String.class.isAssignableFrom( leafClass ) || int.class.isAssignableFrom( leafClass ) || long.class
        .isAssignableFrom( leafClass ) ) {
      // simple classes
      return;
    }
    for ( Field f : leafClass.getDeclaredFields() ) {
      if ( f.isSynthetic() || f.isEnumConstant() || Modifier.isStatic( f.getModifiers() ) ) {
        // fields can't contain real data
        continue;
      }
      BeanLevelInfo leaf = new BeanLevelInfo();
      leaf.parent = this;
      leaf.field = f;
      if ( f.getType().isArray() ) {
        leaf.array = true;
        leaf.leafClass = f.getType().getComponentType();
      } else {
        leaf.array = false;
        leaf.leafClass = f.getType();
      }
      Injection metaInj = f.getAnnotation( Injection.class );
      if ( metaInj != null ) {
        InjectionProperty prop = new InjectionProperty();
        prop.name = metaInj.name();
        prop.group = metaInj.group();
        prop.description = metaInj.description();
        prop.path = leaf.createCallStack();
        info.properties.put( prop.name, prop );
        BeanLevelInfo p = this;
        while ( p != null ) {
          p = p.parent;
        }
      } else {
        leaf.init( info );
      }
    }
  }

  private List<BeanLevelInfo> createCallStack() {
    List<BeanLevelInfo> stack = new ArrayList<>();
    BeanLevelInfo p = this;
    while ( p != null ) {
      if ( p.field != null ) {
        p.field.setAccessible( true );
      }
      stack.add( p );
      p = p.parent;
    }
    Collections.reverse( stack );
    return stack;
  }

  @Override
  public String toString() {
    String r = "";
    if ( field != null ) {
      r += "field " + field.getName();
    } else {
      r += "<root field>";
    }
    r += "(class " + leafClass.getSimpleName() + ")";
    return r;
  }
}
