package org.pentaho.di.core.injection.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.trans.steps.fileinput.text.TextFileInputMeta;

/**
 * Storage for bean annotations info for Metadata Injection and Load/Save.
 *
 */
public class BeanInjectionInfo {
  BeanLevelInfo root;
  Map<String, InjectionProperty> properties = new HashMap<>();
  List<InjectionGroup> groups=new ArrayList<>();

  public static boolean isInjectionSupported(Class<?> clazz) {
    return clazz==TextFileInputMeta.class;
  }
  
  public BeanInjectionInfo( Class<?> clazz ) {
    BeanLevelInfo root = new BeanLevelInfo();
    root.leafClass = clazz;
    root.init( this );
    
    properties=Collections.unmodifiableMap( properties );
    groups=Collections.unmodifiableList( groups );
  }
  
  public Map<String, InjectionProperty> getProperties() {
    return properties;
  }
  
  public List<InjectionGroup> getGroups() {
    return groups;
  }

//  private String extractIndexes( String propName, List<Integer> extractedIndexes ) {
//    Matcher m = RE_INDEX.matcher( propName );
//
//    boolean contains = m.find();
//    if ( !contains ) {
//      return propName;
//    }
//
//    StringBuffer sb = new StringBuffer( propName.length() );
//    do {
//      String orig = propName.substring( m.start() + 1, m.end() - 1 );
//      extractedIndexes.add( Integer.parseInt( orig ) );
//      m.appendReplacement( sb, "[]" );
//      contains = m.find();
//    } while ( contains );
//    m.appendTail( sb );
//    return sb.toString();
//  }

  
  
}
