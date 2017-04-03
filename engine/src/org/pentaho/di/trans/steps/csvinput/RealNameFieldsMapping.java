package org.pentaho.di.trans.steps.csvinput;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;

public class RealNameFieldsMapping implements FieldsMapping {

  private final Map<Integer, Integer> fieldNamesOrderMap = new HashMap<>();

  public RealNameFieldsMapping( String[] actualFieldNames, CsvInputMeta meta ) {
    Map<String, Integer> metaNameToInder = new HashMap<>();
    TextFileInputField[] fields = meta.getInputFields();
    for ( int j = 0; j < fields.length; j++ ) {
      metaNameToInder.put( fields[j].getName(), Integer.valueOf( j ) );
    }

    for ( int i = 0; i < actualFieldNames.length; i++ ) {
      fieldNamesOrderMap.put( i, metaNameToInder.get( actualFieldNames[i] ) );
    }
  }

  @Override
  public int fieldMetaIndex( int currentIndex ) {
    Integer index = fieldNamesOrderMap.get( currentIndex );
    return index == null ? -1 : index.intValue();
  }

  @Override
  public int size() {
    return fieldNamesOrderMap.size();
  }

}
