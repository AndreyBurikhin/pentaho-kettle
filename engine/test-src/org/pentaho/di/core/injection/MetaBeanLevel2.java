package org.pentaho.di.core.injection;

public class MetaBeanLevel2 {
  @Injection( name = "SEPARATOR" )
  private String separator;

  private MetaBeanLevel3[] files;
  
  private String[] filenames;

  public String getSeparator() {
    return separator;
  }

  public MetaBeanLevel3[] getFiles() {
    return files;
  }
}
