/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2016 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.fieldsplitter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.core.injection.BaseMetadataInjectionTest;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaString;

public class FieldSplitterMetaInjectionTest extends BaseMetadataInjectionTest<FieldSplitterMeta> {
  @Before
  public void setup() {
    setup( new FieldSplitterMeta() );
  }

  @Test
  public void test() throws Exception {
    check( "FIELD_TO_SPLIT", new StringGetter() {
      public String get() {
        return meta.getSplitField();
      }
    } );
    check( "DELIMITER", new StringGetter() {
      public String get() {
        return meta.getDelimiter();
      }
    } );
    check( "ENCLOSURE", new StringGetter() {
      public String get() {
        return meta.getEnclosure();
      }
    } );
    check( "NAME", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldName();
      }
    } );
    check( "ID", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldID();
      }
    } );
    check( "REMOVE_ID", new BooleanGetter() {
      public boolean get() {
        return meta.getSplitFields()[0].getFieldRemoveID();
      }
    } );
    check( "FORMAT", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldFormat();
      }
    } );
    check( "GROUPING", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldGroup();
      }
    } );
    check( "DECIMAL", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldDecimal();
      }
    } );
    check( "CURRENCY", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldCurrency();
      }
    } );
    check( "LENGTH", new IntGetter() {
      public int get() {
        return meta.getSplitFields()[0].getFieldLength();
      }
    } );
    check( "PRECISION", new IntGetter() {
      public int get() {
        return meta.getSplitFields()[0].getFieldPrecision();
      }
    } );
    check( "NULL_IF", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldNullIf();
      }
    } );
    check( "DEFAULT", new StringGetter() {
      public String get() {
        return meta.getSplitFields()[0].getFieldIfNull();
      }
    } );

    ValueMetaInterface mftt = new ValueMetaString( "f" );
    injector.setProperty( meta, "TRIM_TYPE", setValue( mftt, "none" ), "f" );
    assertEquals( 0, meta.getSplitFields()[0].getFieldTrimType() );
    injector.setProperty( meta, "TRIM_TYPE", setValue( mftt, "left" ), "f" );
    assertEquals( 1, meta.getSplitFields()[0].getFieldTrimType() );
    injector.setProperty( meta, "TRIM_TYPE", setValue( mftt, "right" ), "f" );
    assertEquals( 2, meta.getSplitFields()[0].getFieldTrimType() );
    injector.setProperty( meta, "TRIM_TYPE", setValue( mftt, "both" ), "f" );
    assertEquals( 3, meta.getSplitFields()[0].getFieldTrimType() );
    skipPropertyTest( "TRIM_TYPE" );

    // TODO check field type plugins
    skipPropertyTest( "DATA_TYPE" );
  }
}
