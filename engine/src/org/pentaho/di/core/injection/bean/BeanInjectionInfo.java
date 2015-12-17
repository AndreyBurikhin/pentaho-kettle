package org.pentaho.di.core.injection.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.injection.Injection;
import org.pentaho.di.core.injection.InjectionSupported;
import org.pentaho.di.i18n.BaseMessages;

/**
 * Storage for bean annotations info for Metadata Injection and Load/Save.
 *
 */
public class BeanInjectionInfo {
  private final Class<?> clazz;
  private final InjectionSupported clazzAnnotation;
  private Map<String, Property> properties = new HashMap<>();
  private List<Group> groups = new ArrayList<>();

  public static boolean isInjectionSupported( Class<?> clazz ) {

    InjectionSupported annotation = clazz.getAnnotation( InjectionSupported.class );
    return annotation != null;
  }

  public BeanInjectionInfo( Class<?> clazz ) {
    this.clazz = clazz;
    clazzAnnotation = clazz.getAnnotation( InjectionSupported.class );
    if ( clazzAnnotation == null ) {
      throw new RuntimeException( "Injection not supported in " + clazz );
    }

    for ( String group : clazzAnnotation.groups() ) {
      groups.add( new Group(group) );
    }

    BeanLevelInfo root = new BeanLevelInfo();
    root.leafClass = clazz;
    root.init( this );

    properties = Collections.unmodifiableMap( properties );
    groups = Collections.unmodifiableList( groups );
  }

  public Map<String, Property> getProperties() {
    return properties;
  }

  public List<Group> getGroups() {
    return groups;
  }

  protected void addInjectionProperty( Injection metaInj, BeanLevelInfo leaf ) {
    Property prop = new Property(metaInj.name(),metaInj.group(),leaf.createCallStack());
    properties.put( prop.name, prop );
    // BeanLevelInfo p = leaf.parent;
    // while ( p != null ) {
    // p = p.parent;
    // }
  }

  // private String extractIndexes( String propName, List<Integer> extractedIndexes ) {
  // Matcher m = RE_INDEX.matcher( propName );
  //
  // boolean contains = m.find();
  // if ( !contains ) {
  // return propName;
  // }
  //
  // StringBuffer sb = new StringBuffer( propName.length() );
  // do {
  // String orig = propName.substring( m.start() + 1, m.end() - 1 );
  // extractedIndexes.add( Integer.parseInt( orig ) );
  // m.appendReplacement( sb, "[]" );
  // contains = m.find();
  // } while ( contains );
  // m.appendTail( sb );
  // return sb.toString();
  // }

  public class Property {
    private final String name;
    private final String groupName;
    protected final List<BeanLevelInfo> path;

    public Property( String name, String groupName, List<BeanLevelInfo> path ) {
      this.name = name;
      this.groupName = groupName;
      this.path = path;
    }

    public String getName() {
      return name;
    }

    public String getGroupName() {
      return groupName;
    }


    public String getDescription() {
      return BaseMessages.getString( clazz, clazzAnnotation.localizationPrefix() + name );
    }
  }

  public class Group {
    private final String name;

    public Group( String name ) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return BaseMessages.getString( clazz, clazzAnnotation.localizationPrefix() + name );
    }
  }
}
