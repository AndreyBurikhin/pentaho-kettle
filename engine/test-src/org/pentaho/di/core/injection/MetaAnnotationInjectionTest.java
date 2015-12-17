package org.pentaho.di.core.injection;

import org.junit.Test;
import org.pentaho.di.core.injection.bean.BeanInjectionInfo;
import org.pentaho.di.core.injection.bean.BeanInjector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MetaAnnotationInjectionTest {

  @Test
  public void testInjectionDescription() throws Exception {

    BeanInjectionInfo ri = new BeanInjectionInfo( MetaBeanLevel1.class );

    assertEquals( 2, ri.getGroups().size() );
    assertEquals( "FILENAME_LINES", ri.getGroups().get( 0 ).getName() );
    assertEquals( "FILENAME_LINES2", ri.getGroups().get( 1 ).getName() );

    assertEquals( 2, ri.getProperties().size() );
    assertTrue( ri.getProperties().containsKey( "SEPARATOR" ) );
    assertTrue( ri.getProperties().containsKey( "FILENAME" ) );

    assertEquals( "FILENAME_LINES", ri.getProperties().get( "FILENAME" ).getGroupName() );

    MetaBeanLevel1 obj = new MetaBeanLevel1();

    BeanInjector inj = new BeanInjector( ri );
    inj.setProperty( obj, "SEPARATOR", "<sep>" );
    inj.setProperty( obj, "FILENAME", "/tmp/file.txt" );

    assertEquals( "<sep>", obj.getSub().getSeparator() );
    assertEquals( "/tmp/file.txt", obj.getSub().getFiles()[0].getName() );
  }
}
