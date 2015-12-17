package org.pentaho.di.core.injection;

@InjectionSupported( localizationPrefix = "", groups = { "FILENAME_LINES", "FILENAME_LINES2" } )
public class MetaBeanLevel1 {

  private MetaBeanLevel2 sub;

  public MetaBeanLevel2 getSub() {
    return sub;
  }
}
