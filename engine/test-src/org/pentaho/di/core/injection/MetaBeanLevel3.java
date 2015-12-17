package org.pentaho.di.core.injection;

public class MetaBeanLevel3 {
  @Injection( name = "FILENAME", group="FILENAME_LINES" )
  private String name;

  public String getName() {
    return name;
  }
}
