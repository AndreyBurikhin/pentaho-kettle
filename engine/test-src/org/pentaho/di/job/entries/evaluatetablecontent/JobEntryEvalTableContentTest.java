package org.pentaho.di.job.entries.evaluatetablecontent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.InfobrightDatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.plugins.DatabasePluginType;
import org.pentaho.di.core.plugins.Plugin;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.PluginTypeInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryCopy;

/*
 * tests fix for PDI-1044
 * Job entry: Evaluate rows number in a table:
 * PDI Server logs with error from Quartz even though the job finishes successfully.
 */
public class JobEntryEvalTableContentTest {
  private static final Map<Class<?>, String> dbMap = new HashMap<Class<?>, String>();
  JobEntryEvalTableContent entry;

  public static class DBMockIface extends BaseDatabaseMeta {

    @Override
    public Object clone() {
      return this;
    }

    @Override
    public String getFieldDefinition( ValueMetaInterface v, String tk, String pk, boolean use_autoinc,
      boolean add_fieldname, boolean add_cr ) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String getDriverClass() {
      return "org.pentaho.di.job.entries.evaluatetablecontent.MockDriver";
    }

    @Override
    public String getURL( String hostname, String port, String databaseName ) throws KettleDatabaseException {
      return "";
    }

    @Override
    public String getAddColumnStatement( String tablename, ValueMetaInterface v, String tk, boolean use_autoinc,
      String pk, boolean semicolon ) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String getModifyColumnStatement( String tablename, ValueMetaInterface v, String tk,
      boolean use_autoinc, String pk, boolean semicolon ) {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String[] getUsedLibraries() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public int[] getAccessTypeList() {
      // TODO Auto-generated method stub
      return null;
    }

  }

  // private static DBMockIface dbi = DBMockIface();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    PluginRegistry.getInstance().registerPluginType( ValueMetaPluginType.class );

    Map<Class<?>, String> classes = new HashMap<Class<?>, String>();
    classes.put( ValueMetaInterface.class, "org.pentaho.di.core.row.value.ValueMetaString" );
    Plugin p1 =
      new Plugin(
        new String[] { "2" }, ValueMetaPluginType.class, ValueMetaInterface.class, "", "", "", "", false,
        true, classes, null, null, null );

    classes = new HashMap<Class<?>, String>();
    classes.put( ValueMetaInterface.class, "org.pentaho.di.core.row.value.ValueMetaInteger" );
    Plugin p2 =
      new Plugin(
        new String[] { "5" }, ValueMetaPluginType.class, ValueMetaInterface.class, "", "", "", "", false,
        true, classes, null, null, null );

    PluginRegistry.getInstance().registerPlugin( ValueMetaPluginType.class, p1 );
    PluginRegistry.getInstance().registerPlugin( ValueMetaPluginType.class, p2 );

    KettleLogStore.init();

    dbMap.put( DatabaseInterface.class, DBMockIface.class.getName() );
    dbMap.put( InfobrightDatabaseMeta.class, InfobrightDatabaseMeta.class.getName() );

    PluginRegistry preg = PluginRegistry.getInstance();

    PluginInterface mockDbPlugin = mock( PluginInterface.class );
    when( mockDbPlugin.matches( anyString() ) ).thenReturn( true );
    when( mockDbPlugin.isNativePlugin() ).thenReturn( true );
    when( mockDbPlugin.getMainType() ).thenAnswer( new Answer<Class<?>>() {
      @Override
      public Class<?> answer( InvocationOnMock invocation ) throws Throwable {
        return DatabaseInterface.class;
      }
    } );

    when( mockDbPlugin.getPluginType() ).thenAnswer( new Answer<Class<? extends PluginTypeInterface>>() {
      @Override
      public Class<? extends PluginTypeInterface> answer( InvocationOnMock invocation ) throws Throwable {
        return DatabasePluginType.class;
      }
    } );

    when( mockDbPlugin.getIds() ).thenReturn( new String[] { "Oracle", "mock-db-id" } );
    when( mockDbPlugin.getName() ).thenReturn( "mock-db-name" );
    when( mockDbPlugin.getClassMap() ).thenReturn( dbMap );

    preg.registerPlugin( DatabasePluginType.class, mockDbPlugin );
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    MockDriver.registerInstance();
    Job job = new Job( null, new JobMeta() );
    entry = new JobEntryEvalTableContent();

    job.getJobMeta().addJobEntry( new JobEntryCopy( entry ) );
    entry.setParentJob( job );

    job.setStopped( false );

    DatabaseMeta dbMeta = new DatabaseMeta();
    dbMeta.setDatabaseType( "mock-db" );

    entry.setDatabase( dbMeta );
  }

  @After
  public void tearDown() throws Exception {
    MockDriver.deregeisterInstances();
  }

  @Test
  public void testNrErrorsFailureNewBehavior() throws Exception {
    entry.limit = "1";
    entry.successCondition = JobEntryEvalTableContent.SUCCESS_CONDITION_ROWS_COUNT_EQUAL;
    entry.tablename = "table";

    Result res = entry.execute( new Result(), 0 );

    assertFalse( "Eval number of rows should fail", res.getResult() );
    assertEquals(
      "No errors should be reported in result object accoding to the new behavior", res.getNrErrors(), 0 );
  }

  @Test
  public void testNrErrorsFailureOldBehavior() throws Exception {
    entry.limit = "1";
    entry.successCondition = JobEntryEvalTableContent.SUCCESS_CONDITION_ROWS_COUNT_EQUAL;
    entry.tablename = "table";

    entry.setVariable( Const.KETTLE_COMPATIBILITY_SET_ERROR_ON_SPECIFIC_JOB_ENTRIES, "Y" );

    Result res = entry.execute( new Result(), 0 );

    assertFalse( "Eval number of rows should fail", res.getResult() );
    assertEquals(
      "An error should be reported in result object accoding to the old behavior", res.getNrErrors(), 1 );
  }

  @Test
  public void testNrErrorsSuccess() throws Exception {
    entry.limit = "5";
    entry.successCondition = JobEntryEvalTableContent.SUCCESS_CONDITION_ROWS_COUNT_EQUAL;
    entry.tablename = "table";

    Result res = entry.execute( new Result(), 0 );

    assertTrue( "Eval number of rows should be suceeded", res.getResult() );
    assertEquals( "Apparently there should no error", res.getNrErrors(), 0 );

    // that should work regardless of old/new behavior flag
    entry.setVariable( Const.KETTLE_COMPATIBILITY_SET_ERROR_ON_SPECIFIC_JOB_ENTRIES, "Y" );

    res = entry.execute( new Result(), 0 );

    assertTrue( "Eval number of rows should be suceeded", res.getResult() );
    assertEquals( "Apparently there should no error", res.getNrErrors(), 0 );
  }

  @Test
  public void testNrErrorsNoCustomSql() throws Exception {
    entry.limit = "5";
    entry.successCondition = JobEntryEvalTableContent.SUCCESS_CONDITION_ROWS_COUNT_EQUAL;
    entry.iscustomSQL = true;
    entry.customSQL = null;

    Result res = entry.execute( new Result(), 0 );

    assertFalse( "Eval number of rows should fail", res.getResult() );
    assertEquals( "Apparently there should be an error", res.getNrErrors(), 1 );

    // that should work regardless of old/new behavior flag
    entry.setVariable( Const.KETTLE_COMPATIBILITY_SET_ERROR_ON_SPECIFIC_JOB_ENTRIES, "Y" );

    res = entry.execute( new Result(), 0 );

    assertFalse( "Eval number of rows should fail", res.getResult() );
    assertEquals( "Apparently there should be an error", res.getNrErrors(), 1 );
  }
}
