/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
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

import java.util.Arrays;
import java.util.List;

import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.injection.Injection;
import org.pentaho.di.core.injection.InjectionDeep;
import org.pentaho.di.core.injection.InjectionSupported;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaFactory;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInjectionInterface;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

/*
 * Created on 31-okt-2003
 *
 */

/**
 * <CODE>
  Example1:<p>
  -------------<p>
  DATUM;VALUES<p>
  20031031;500,300,200,100<p>
<p>
        ||<t>        delimiter     = ,<p>
       \||/<t>       field[]       = SALES1, SALES2, SALES3, SALES4<p>
        \/<t>        id[]          = <empty><p>
          <t>        idrem[]       = no, no, no, no<p>
           <t>       type[]        = Number, Number, Number, Number<p>
            <t>      format[]      = ###.##, ###.##, ###.##, ###.##<p>
            <t>      group[]       = <empty><p>
            <t>      decimal[]     = .<p>
            <t>      currency[]    = <empty><p>
            <t>      length[]      = 3, 3, 3, 3<p>
            <t>      precision[]   = 0, 0, 0, 0<p>
  <p>
  DATUM;SALES1;SALES2;SALES3;SALES4<p>
  20031031;500;300;200;100<p>
<p>
  Example2:<p>
  -----------<p>
<p>
  20031031;Sales2=310.50, Sales4=150.23<p>
<p>
        ||        delimiter     = ,<p>
       \||/       field[]       = SALES1, SALES2, SALES3, SALES4<p>
        \/        id[]          = Sales1, Sales2, Sales3, Sales4<p>
                  idrem[]       = yes, yes, yes, yes (remove ID's from split field)<p>
                  type[]        = Number, Number, Number, Number<p>
                  format[]      = ###.##, ###.##, ###.##, ###.##<p>
                  group[]       = <empty><p>
                  decimal[]     = .<p>
                  currency[]    = <empty><p>
                  length[]      = 3, 3, 3, 3<p>
                  precision[]   = 0, 0, 0, 0<p>
<p>
  DATUM;SALES1;SALES2;SALES3;SALES4<p>
  20031031;310,50;150,23<p>
<p>

</CODE>
 **/
@InjectionSupported( localizationPrefix = "FieldSplitter.Injection.", groups = { "FIELDS" } )
public class FieldSplitterMeta extends BaseStepMeta implements StepMetaInterface {
  private static Class<?> PKG = FieldSplitterMeta.class; // for i18n purposes, needed by Translator2!!

  /** Field to split */
  @Injection( name = "FIELD_TO_SPLIT" )
  private String splitField;

  /** Split fields based upon this delimiter. */
  @Injection( name = "DELIMITER" )
  private String delimiter;

  /** Ignore delimiter inside pairs of the enclosure string */
  @Injection( name = "ENCLOSURE" )
  private String enclosure;
  
  @InjectionDeep
  private SplitField[] splitFields;

  public static class SplitField implements Cloneable {
    /** new field names */
    @Injection( name = "NAME", group = "FIELDS" )
    private String fieldName;

    /** Field ID's to scan for */
    @Injection( name = "ID", group = "FIELDS" )
    private String fieldID;

    /** flag: remove ID */
    @Injection( name = "REMOVE_ID", group = "FIELDS" )
    private boolean fieldRemoveID;

    /** type of new field */
    private int fieldType;

    /** formatting mask to convert value */
    @Injection( name = "FORMAT", group = "FIELDS" )
    private String fieldFormat;

    /** Grouping symbol */
    @Injection( name = "GROUPING", group = "FIELDS" )
    private String fieldGroup;

    /** Decimal point . or , */
    @Injection( name = "DECIMAL", group = "FIELDS" )
    private String fieldDecimal;

    /** Currency symbol */
    @Injection( name = "CURRENCY", group = "FIELDS" )
    private String fieldCurrency;

    /** Length of field */
    @Injection( name = "LENGTH", group = "FIELDS" )
    private int fieldLength;

    /** Precision of field */
    @Injection( name = "PRECISION", group = "FIELDS" )
    private int fieldPrecision;

    /** Replace this value with a null */
    @Injection( name = "NULL_IF", group = "FIELDS" )
    private String fieldNullIf;

    /** Default value in case no value was found (ID option) */
    @Injection( name = "DEFAULT", group = "FIELDS" )
    private String fieldIfNull;

    /** Perform trimming of this type on the fieldName during lookup and storage */
    private int fieldTrimType;
    
    public String getFieldName() {
      return fieldName;
    }

    public void setFieldName( final String fieldName ) {
      this.fieldName = fieldName;
    }

    public String getFieldID() {
      return fieldID;
    }

    public void setFieldID( final String fieldID ) {
      this.fieldID = fieldID;
    }

    public boolean getFieldRemoveID() {
      return fieldRemoveID;
    }

    public void setFieldRemoveID( final boolean fieldRemoveID ) {
      this.fieldRemoveID = fieldRemoveID;
    }

    public int getFieldType() {
      return fieldType;
    }

    public void setFieldType( final int fieldType ) {
      this.fieldType = fieldType;
    }

    public String getFieldFormat() {
      return fieldFormat;
    }

    public void setFieldFormat( final String fieldFormat ) {
      this.fieldFormat = fieldFormat;
    }

    public String getFieldGroup() {
      return fieldGroup;
    }

    public void setFieldGroup( final String fieldGroup ) {
      this.fieldGroup = fieldGroup;
    }

    public String getFieldDecimal() {
      return fieldDecimal;
    }

    public void setFieldDecimal( final String fieldDecimal ) {
      this.fieldDecimal = fieldDecimal;
    }

    public String getFieldCurrency() {
      return fieldCurrency;
    }

    public void setFieldCurrency( final String fieldCurrency ) {
      this.fieldCurrency = fieldCurrency;
    }

    public int getFieldLength() {
      return fieldLength;
    }

    public void setFieldLength( final int fieldLength ) {
      this.fieldLength = fieldLength;
    }

    public int getFieldPrecision() {
      return fieldPrecision;
    }

    public void setFieldPrecision( final int fieldPrecision ) {
      this.fieldPrecision = fieldPrecision;
    }

    public String getFieldNullIf() {
      return fieldNullIf;
    }

    public void setFieldNullIf( final String fieldNullIf ) {
      this.fieldNullIf = fieldNullIf;
    }

    public String getFieldIfNull() {
      return fieldIfNull;
    }

    public void setFieldIfNull( final String fieldIfNull ) {
      this.fieldIfNull = fieldIfNull;
    }

    public int getFieldTrimType() {
      return fieldTrimType;
    }

    public void setFieldTrimType( final int fieldTrimType ) {
      this.fieldTrimType = fieldTrimType;
    }
    
    @Injection( name = "DATA_TYPE", group = "FIELDS" )
    public void setFieldType( String value ) {
      fieldType = ValueMeta.getType( value );
    }

    @Injection( name = "TRIM_TYPE", group = "FIELDS" )
    public void setFieldTrimType( String value ) {
      fieldTrimType = ValueMeta.getTrimTypeByCode( value );
    }

    public Object clone() {
      try {
        Object retval = super.clone();
        return retval;
      } catch ( CloneNotSupportedException e ) {
        return null;
      }
    }
  }

  public FieldSplitterMeta() {
    super(); // allocate BaseStepMeta
  }

  public String getSplitField() {
    return splitField;
  }

  public void setSplitField( final String splitField ) {
    this.splitField = splitField;
  }

  public String getDelimiter() {
    return delimiter;
  }

  public void setDelimiter( final String delimiter ) {
    this.delimiter = delimiter;
  }

  public String getEnclosure() {
    return enclosure;
  }

  public void setEnclosure( final String enclosure ) {
    this.enclosure = enclosure;
  }
  
  public SplitField[] getSplitFields() {
    return splitFields;
  }

  public void setSplitFields( SplitField[] splitFields ) {
    this.splitFields = splitFields;
  }

  public void loadXML( Node stepnode, List<DatabaseMeta> databases, IMetaStore metaStore ) throws KettleXMLException {
    readData( stepnode );
  }

  public void allocate( int nrfields ) {
    splitFields = new SplitField[nrfields];
    for ( int i = 0; i < splitFields.length; i++ ) {
      splitFields[i] = new SplitField();
    }
  }

  public Object clone() {
    FieldSplitterMeta retval = (FieldSplitterMeta) super.clone();

    final int nrfields = splitFields.length;

    retval.allocate( nrfields );

    for ( int i = 0; i < nrfields; i++ ) {
      retval.splitFields[i] = (SplitField) splitFields[i].clone();
      
//      retval.fieldName[i] = fieldName[i];
//      retval.fieldID[i] = fieldID[i];
//      retval.fieldRemoveID[i] = fieldRemoveID[i];
//      retval.fieldType[i] = fieldType[i];
//      retval.fieldLength[i] = fieldLength[i];
//      retval.fieldPrecision[i] = fieldPrecision[i];
//      retval.fieldFormat[i] = fieldFormat[i];
//      retval.fieldGroup[i] = fieldGroup[i];
//      retval.fieldDecimal[i] = fieldDecimal[i];
//      retval.fieldCurrency[i] = fieldCurrency[i];
//      retval.fieldNullIf[i] = fieldNullIf[i];
//      retval.fieldIfNull[i] = fieldIfNull[i];
//      retval.fieldTrimType[i] = fieldTrimType[i];
    }

    return retval;
  }

  private void readData( Node stepnode ) throws KettleXMLException {
    try {
      splitField = XMLHandler.getTagValue( stepnode, "splitfield" );
      delimiter = XMLHandler.getTagValue( stepnode, "delimiter" );
      enclosure = XMLHandler.getTagValue( stepnode, "enclosure" );

      final Node fields = XMLHandler.getSubNode( stepnode, "fields" );
      final int nrfields = XMLHandler.countNodes( fields, "field" );

      allocate( nrfields );

      for ( int i = 0; i < nrfields; i++ ) {
        final Node fnode = XMLHandler.getSubNodeByNr( fields, "field", i );
        splitFields[i] = new SplitField();
        
        splitFields[i].setFieldName( XMLHandler.getTagValue( fnode, "name" ) );
        splitFields[i].setFieldID( XMLHandler.getTagValue( fnode, "id" ) );
        final String sidrem = XMLHandler.getTagValue( fnode, "idrem" );
        final String stype = XMLHandler.getTagValue( fnode, "type" );
        splitFields[i].setFieldFormat( XMLHandler.getTagValue( fnode, "format" ) );
        splitFields[i].setFieldGroup( XMLHandler.getTagValue( fnode, "group" ) );
        splitFields[i].setFieldDecimal( XMLHandler.getTagValue( fnode, "decimal" ) );
        splitFields[i].setFieldCurrency( XMLHandler.getTagValue( fnode, "currency" ) );
        final String slen = XMLHandler.getTagValue( fnode, "length" );
        final String sprc = XMLHandler.getTagValue( fnode, "precision" );
        splitFields[i].setFieldNullIf( XMLHandler.getTagValue( fnode, "nullif" ) );
        splitFields[i].setFieldIfNull( XMLHandler.getTagValue( fnode, "ifnull" ) );
        final String trim = XMLHandler.getTagValue( fnode, "trimtype" );

        splitFields[i].setFieldRemoveID( "Y".equalsIgnoreCase( sidrem ) );
        splitFields[i].setFieldType( ValueMeta.getType( stype ) );
        splitFields[i].setFieldLength( Const.toInt( slen, -1 ) );
        splitFields[i].setFieldPrecision( Const.toInt( sprc, -1 ) );
        splitFields[i].setFieldTrimType( ValueMeta.getTrimTypeByCode( trim ) );
      }
    } catch ( Exception e ) {
      throw new KettleXMLException( BaseMessages.getString(
        PKG, "FieldSplitterMeta.Exception.UnableToLoadStepInfoFromXML" ), e );
    }
  }

  public void setDefault() {
    splitField = "";
    delimiter = ",";
    enclosure = null;
    allocate( 0 );
  }

  public void getFields( RowMetaInterface r, String name, RowMetaInterface[] info, StepMeta nextStep,
    VariableSpace space, Repository repository, IMetaStore metaStore ) throws KettleStepException {
    // Remove the field to split
    int idx = r.indexOfValue( getSplitField() );
    if ( idx < 0 ) { // not found
      throw new RuntimeException( BaseMessages.getString(
        PKG, "FieldSplitter.Log.CouldNotFindFieldToSplit", getSplitField() ) );
    }

    // Add the new fields at the place of the index --> replace!
    for ( int i = 0; i < getSplitFields().length; i++ ) {
      try {
        final ValueMetaInterface v = ValueMetaFactory.createValueMeta( getSplitFields()[i].getFieldName(), getSplitFields()[i].getFieldType() );
        v.setLength( getSplitFields()[i].getFieldLength(), getSplitFields()[i].getFieldPrecision() );
        v.setOrigin( name );
        v.setConversionMask( getSplitFields()[i].getFieldFormat() );
        v.setDecimalSymbol( getSplitFields()[i].getFieldDecimal() );
        v.setGroupingSymbol( getSplitFields()[i].getFieldGroup() );
        v.setCurrencySymbol( getSplitFields()[i].getFieldCurrency() );
        v.setTrimType( getSplitFields()[i].getFieldTrimType() );
        // TODO when implemented in UI
        // v.setDateFormatLenient(dateFormatLenient);
        // TODO when implemented in UI
        // v.setDateFormatLocale(dateFormatLocale);
        if ( i == 0 && idx >= 0 ) {
          // the first valueMeta (splitField) will be replaced
          r.setValueMeta( idx, v );
        } else {
          // other valueMeta will be added
          if ( idx >= r.size() ) {
            r.addValueMeta( v );
          }
          r.addValueMeta( idx + i, v );
        }
      } catch ( Exception e ) {
        throw new KettleStepException( e );
      }
    }
  }

  public String getXML() {
    final StringBuilder retval = new StringBuilder( 500 );

    retval.append( "   " ).append( XMLHandler.addTagValue( "splitfield", splitField ) );
    retval.append( "   " ).append( XMLHandler.addTagValue( "delimiter", delimiter ) );
    retval.append( "   " ).append( XMLHandler.addTagValue( "enclosure", enclosure ) );

    retval.append( "    <fields>" );
    for ( int i = 0; i < splitFields.length; i++ ) {
      retval.append( "      <field>" );
      retval.append( "        " ).append( XMLHandler.addTagValue( "name", splitFields[i].getFieldName() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "id", splitFields[i].getFieldID( ) ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "idrem", splitFields[i].getFieldRemoveID() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "type", ValueMeta.getTypeDesc( splitFields[i].getFieldType() ) ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "format", splitFields[i].getFieldFormat() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "group", splitFields[i].getFieldGroup() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "decimal", splitFields[i].getFieldDecimal() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "currency", splitFields[i].getFieldCurrency() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "length", splitFields[i].getFieldLength() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "precision", splitFields[i].getFieldPrecision() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "nullif", splitFields[i].getFieldNullIf() ) );
      retval.append( "        " ).append( XMLHandler.addTagValue( "ifnull", splitFields[i].getFieldIfNull() ) );
      retval.append( "        " ).append(
        XMLHandler.addTagValue( "trimtype", ValueMeta.getTrimTypeCode( splitFields[i].getFieldTrimType() ) ) );
      retval.append( "      </field>" );
    }
    retval.append( "    </fields>" );

    return retval.toString();
  }

  public void readRep( Repository rep, IMetaStore metaStore, ObjectId id_step, List<DatabaseMeta> databases ) throws KettleException {
    try {
      splitField = rep.getStepAttributeString( id_step, "splitfield" );
      delimiter = rep.getStepAttributeString( id_step, "delimiter" );
      enclosure = rep.getStepAttributeString( id_step, "enclosure" );

      int nrfields = rep.countNrStepAttributes( id_step, "field_name" );

      allocate( nrfields );

      for ( int i = 0; i < nrfields; i++ ) {
        splitFields[i] = new SplitField();
        splitFields[i].setFieldName( rep.getStepAttributeString( id_step, i, "field_name" ) );
        splitFields[i].setFieldID( rep.getStepAttributeString( id_step, i, "field_id" ) );
        splitFields[i].setFieldRemoveID( rep.getStepAttributeBoolean( id_step, i, "field_idrem" ) );
        splitFields[i].setFieldType( ValueMeta.getType( rep.getStepAttributeString( id_step, i, "field_type" ) ) );
        splitFields[i].setFieldFormat( rep.getStepAttributeString( id_step, i, "field_format" ) );
        splitFields[i].setFieldGroup( rep.getStepAttributeString( id_step, i, "field_group" ) );
        splitFields[i].setFieldDecimal( rep.getStepAttributeString( id_step, i, "field_decimal" ) );
        splitFields[i].setFieldCurrency( rep.getStepAttributeString( id_step, i, "field_currency" ) );
        splitFields[i].setFieldLength( (int) rep.getStepAttributeInteger( id_step, i, "field_length" ) );
        splitFields[i].setFieldPrecision( (int) rep.getStepAttributeInteger( id_step, i, "field_precision" ) );
        splitFields[i].setFieldNullIf( rep.getStepAttributeString( id_step, i, "field_nullif" ) );
        splitFields[i].setFieldIfNull( rep.getStepAttributeString( id_step, i, "field_ifnull" ) );
        splitFields[i].setFieldTrimType(
          ValueMeta.getTrimTypeByCode( rep.getStepAttributeString( id_step, i, "field_trimtype" ) ) );
      }
    } catch ( Exception e ) {
      throw new KettleException( BaseMessages.getString(
        PKG, "FieldSplitterMeta.Exception.UnexpectedErrorInReadingStepInfo" ), e );
    }
  }

  public void saveRep( Repository rep, IMetaStore metaStore, ObjectId id_transformation, ObjectId id_step ) throws KettleException {
    try {
      rep.saveStepAttribute( id_transformation, id_step, "splitfield", splitField );
      rep.saveStepAttribute( id_transformation, id_step, "delimiter", delimiter );
      rep.saveStepAttribute( id_transformation, id_step, "enclosure", enclosure );

      for ( int i = 0; i < splitFields.length; i++ ) {
        rep.saveStepAttribute( id_transformation, id_step, i, "field_name", splitFields[i].getFieldName() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_id", splitFields[i].getFieldID() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_idrem", splitFields[i].getFieldRemoveID() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_type", ValueMeta.getTypeDesc( splitFields[i].getFieldType() ) );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_format", splitFields[i].getFieldFormat() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_group", splitFields[i].getFieldGroup() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_decimal", splitFields[i].getFieldDecimal() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_currency", splitFields[i].getFieldCurrency() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_length", splitFields[i].getFieldLength() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_precision", splitFields[i].getFieldPrecision() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_nullif", splitFields[i].getFieldNullIf() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_ifnull", splitFields[i].getFieldIfNull() );
        rep.saveStepAttribute( id_transformation, id_step, i, "field_trimtype", ValueMeta
          .getTrimTypeCode( splitFields[i].getFieldTrimType() ) );
      }
    } catch ( Exception e ) {
      throw new KettleException( BaseMessages.getString(
        PKG, "FieldSplitterMeta.Exception.UnalbeToSaveStepInfoToRepository" )
        + id_step, e );
    }
  }

  public void check( List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta,
    RowMetaInterface prev, String[] input, String[] output, RowMetaInterface info, VariableSpace space,
    Repository repository, IMetaStore metaStore ) {
    String error_message = "";
    CheckResult cr;

    // Look up fields in the input stream <prev>
    if ( prev != null && prev.size() > 0 ) {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_OK, BaseMessages.getString(
          PKG, "FieldSplitterMeta.CheckResult.StepReceivingFields", prev.size() + "" ), stepMeta );
      remarks.add( cr );

      error_message = "";

      int i = prev.indexOfValue( splitField );
      if ( i < 0 ) {
        error_message =
          BaseMessages.getString(
            PKG, "FieldSplitterMeta.CheckResult.SplitedFieldNotPresentInInputStream", splitField );
        cr = new CheckResult( CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta );
        remarks.add( cr );
      } else {
        cr =
          new CheckResult( CheckResult.TYPE_RESULT_OK, BaseMessages.getString(
            PKG, "FieldSplitterMeta.CheckResult.SplitedFieldFoundInInputStream", splitField ), stepMeta );
        remarks.add( cr );
      }
    } else {
      error_message =
        BaseMessages.getString( PKG, "FieldSplitterMeta.CheckResult.CouldNotReadFieldsFromPreviousStep" )
          + Const.CR;
      cr = new CheckResult( CheckResult.TYPE_RESULT_ERROR, error_message, stepMeta );
      remarks.add( cr );
    }

    // See if we have input streams leading to this step!
    if ( input.length > 0 ) {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_OK, BaseMessages.getString(
          PKG, "FieldSplitterMeta.CheckResult.StepReceivingInfoFromOtherStep" ), stepMeta );
      remarks.add( cr );
    } else {
      cr =
        new CheckResult( CheckResult.TYPE_RESULT_ERROR, BaseMessages.getString(
          PKG, "FieldSplitterMeta.CheckResult.NoInputReceivedFromOtherStep" ), stepMeta );
      remarks.add( cr );
    }
  }

  public StepInterface getStep( StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr,
    TransMeta transMeta, Trans trans ) {
    return new FieldSplitter( stepMeta, stepDataInterface, cnr, transMeta, trans );
  }

  public StepDataInterface getStepData() {
    return new FieldSplitterData();
  }
}
