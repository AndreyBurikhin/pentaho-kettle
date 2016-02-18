/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2013 by Pentaho : http://www.pentaho.com
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.QueueRowSet;
import org.pentaho.di.core.RowSet;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.logging.LoggingObjectInterface;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaString;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.fieldsplitter.FieldSplitterMeta.SplitField;
import org.pentaho.di.trans.steps.mock.StepMockHelper;
import org.pentaho.metastore.api.IMetaStore;

/**
 * Tests for FieldSplitter step
 *
 * @author Pavel Sakun
 * @see FieldSplitter
 */
public class FieldSplitterTest {
  StepMockHelper<FieldSplitterMeta, FieldSplitterData> smh;

  @Before
  public void setUp() {
    smh =
        new StepMockHelper<FieldSplitterMeta, FieldSplitterData>( "Field Splitter", FieldSplitterMeta.class,
            FieldSplitterData.class );
    when( smh.logChannelInterfaceFactory.create( any(), any( LoggingObjectInterface.class ) ) ).thenReturn(
        smh.logChannelInterface );
    when( smh.trans.isRunning() ).thenReturn( true );
  }

  private RowSet mockInputRowSet() {
    return smh.getMockInputRowSet( new Object[][] { { "before", "b=b;c=c", "after" } } );
  }

  private FieldSplitterMeta mockProcessRowMeta() throws KettleStepException {
    FieldSplitterMeta processRowMeta = smh.processRowsStepMetaInterface;
    doReturn( "field to split" ).when( processRowMeta ).getSplitField();
    doCallRealMethod().when( processRowMeta ).getFields( any( RowMetaInterface.class ), anyString(),
        any( RowMetaInterface[].class ), any( StepMeta.class ), any( VariableSpace.class ), any( Repository.class ),
        any( IMetaStore.class ) );

    SplitField[] splitFieds = new SplitField[2];
    SplitField splitFied1 = new SplitField();
    splitFied1.setFieldName( "a" );
    splitFied1.setFieldType( ValueMetaInterface.TYPE_STRING );
    splitFied1.setFieldID( "a=" );
    splitFied1.setFieldRemoveID( false );
    splitFied1.setFieldLength( -1 );
    splitFied1.setFieldPrecision( -1 );
    splitFied1.setFieldTrimType( 0 );
    splitFied1.setFieldFormat( null );
    splitFied1.setFieldDecimal( null );
    splitFied1.setFieldGroup( null );
    splitFied1.setFieldCurrency( null );
    splitFied1.setFieldNullIf( null );
    splitFied1.setFieldIfNull( null );
    splitFieds[0] = splitFied1;

    SplitField splitFied2 = new SplitField();
    splitFied2.setFieldName( "b" );
    splitFied2.setFieldType( ValueMetaInterface.TYPE_STRING );
    splitFied2.setFieldID( "b=" );
    splitFied2.setFieldRemoveID( false );
    splitFied2.setFieldLength( -1 );
    splitFied2.setFieldPrecision( -1 );
    splitFied2.setFieldTrimType( 0 );
    splitFied2.setFieldFormat( null );
    splitFied2.setFieldDecimal( null );
    splitFied2.setFieldGroup( null );
    splitFied2.setFieldCurrency( null );
    splitFied2.setFieldNullIf( null );
    splitFied2.setFieldIfNull( null );
    splitFieds[1] = splitFied2;
    doReturn( splitFieds ).when( processRowMeta ).getSplitFields();

    doReturn( ";" ).when( processRowMeta ).getDelimiter();

    return processRowMeta;
  }

  private RowMeta getInputRowMeta() {
    RowMeta inputRowMeta = new RowMeta();
    inputRowMeta.addValueMeta( new ValueMetaString( "before" ) );
    inputRowMeta.addValueMeta( new ValueMetaString( "field to split" ) );
    inputRowMeta.addValueMeta( new ValueMetaString( "after" ) );

    return inputRowMeta;
  }

  @Test
  public void testSplitFields() throws KettleException {
    KettleEnvironment.init();

    FieldSplitter step = new FieldSplitter( smh.stepMeta, smh.stepDataInterface, 0, smh.transMeta, smh.trans );
    step.init( smh.initStepMetaInterface, smh.stepDataInterface );
    step.setInputRowMeta( getInputRowMeta() );
    step.getInputRowSets().add( mockInputRowSet() );
    step.getOutputRowSets().add( new QueueRowSet() );

    boolean hasMoreRows;
    do {
      hasMoreRows = step.processRow( mockProcessRowMeta(), smh.processRowsStepDataInterface );
    } while ( hasMoreRows );

    RowSet outputRowSet = step.getOutputRowSets().get( 0 );
    Object[] actualRow = outputRowSet.getRow();
    Object[] expectedRow = new Object[] { "before", null, "b=b", "after" };

    Assert.assertEquals( "Output row is of an unexpected length", expectedRow.length, outputRowSet.getRowMeta().size() );

    for ( int i = 0; i < expectedRow.length; i++ ) {
      Assert.assertEquals( "Unexpected output value at index " + i, expectedRow[i], actualRow[i] );
    }
  }
}
