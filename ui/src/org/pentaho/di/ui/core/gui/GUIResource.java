//CHECKSTYLE:FileLength:OFF
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

package org.pentaho.di.ui.core.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.SwtUniversalImage;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.PluginTypeListener;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.svg.SvgSupport;
import org.pentaho.di.laf.BasePropertyHandler;
import org.pentaho.di.ui.core.ConstUI;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.util.ImageUtil;
import org.pentaho.di.ui.util.SwtSvgImageUtil;

/*
 * colors etc. are allocated once and released once at the end of the program.
 *
 * @author Matt
 * @since 27/10/2005
 *
 */
public class GUIResource {

  private static LogChannelInterface log = new LogChannel( "GUIResource" );

  private static GUIResource guiResource;

  private Display display;

  // 33 resources

  /* * * Colors * * */
  private ManagedColor colorBackground;

  private ManagedColor colorGraph;

  private ManagedColor colorTab;

  private ManagedColor colorRed;

  private ManagedColor colorSuccessGreen;

  private ManagedColor colorBlueCustomGrid;

  private ManagedColor colorGreen;

  private ManagedColor colorBlue;

  private ManagedColor colorOrange;

  private ManagedColor colorYellow;

  private ManagedColor colorMagenta;

  private ManagedColor colorBlack;

  private ManagedColor colorGray;

  private ManagedColor colorDarkGray;

  private ManagedColor colorLightGray;

  private ManagedColor colorDemoGray;

  private ManagedColor colorWhite;

  private ManagedColor colorDirectory;

  private ManagedColor colorPentaho;

  private ManagedColor colorLightPentaho;

  private ManagedColor colorCreamPentaho;

  private ManagedColor colorLightBlue;

  private ManagedColor colorCrystalTextPentaho;

  /* * * Fonts * * */
  private ManagedFont fontGraph;

  private ManagedFont fontNote;

  private ManagedFont fontFixed;

  private ManagedFont fontMedium;

  private ManagedFont fontMediumBold;

  private ManagedFont fontLarge;

  private ManagedFont fontTiny;

  private ManagedFont fontSmall;

  private ManagedFont fontBold;

  /* * * Images * * */
  private Map<String, SwtUniversalImage> imagesSteps = new Hashtable<String, SwtUniversalImage>();

  private Map<String, Image> imagesStepsSmall = new Hashtable<String, Image>();

  private Map<String, SwtUniversalImage> imagesJobentries;

  private Map<String, Image> imagesJobentriesSmall;

  private Image imageHop;

  private Image imageDisabledHop;

  private SwtUniversalImage imageConnection;

  private Image imageAdd;

  private Image imageTable;

  private Image imageKeySmall;

  private Image imageColumnSmall;

  private Image imageSchema;

  private Image imageSynonym;

  private Image imageProcedure;

  private Image imageExploreDbSmall;

  private Image imageView;

  private Image imageKettleLogo;

  private SwtUniversalImage imageLogoSmall;

  private Image imageBanner;

  private SwtUniversalImage imageBol;

  private Image imageCalendar;

  private Image imageCluster;

  private Image imageSlave;

  private SwtUniversalImage imageArrow;

  private SwtUniversalImage imageFolder;

  private Image imageWizard;

  private Image imageCredits;

  private SwtUniversalImage imageStart;

  private SwtUniversalImage imageDummy;

  private Image imageSpoon;

  private Image imageJob;

  private Image imagePentaho;

  private Image imagePentahoSwirl;

  private Image imageVariable;

  private SwtUniversalImage imageTransGraph;

  private SwtUniversalImage imageJobGraph;

  private Image imageUser;

  private Image imageProfil;

  private Image imageFolderConnections;

  private Image imageEditOptionButton;

  private Image imageEditSmall;

  private Image imageExploreSolutionSmall;

  private Image imageColor;

  private Image imageNoteSmall;

  private Image imageResetOptionButton;

  private Image imageShowLog;

  private Image imageShowGrid;

  private Image imageShowHistory;

  private Image imageShowPerf;

  private Image imageShowInactive;

  private Image imageHideInactive;

  private Image imageShowSelected;

  private Image imageShowAll;

  private Image imageClosePanel;

  private Image imageMaximizePanel;

  private Image imageMinimizePanel;

  private Image imageShowErrorLines;

  private Image imageShowResults;

  private Image imageHideResults;

  private Image imageDesignPanel;

  private Image imageViewPanel;

  private Image imageExpandAll;

  private Image imageSearchSmall;

  private Image imageRegExSmall;

  private Image imageCollapseAll;

  private SwtUniversalImage imageStepError;

  private SwtUniversalImage imageCopyHop;

  private SwtUniversalImage imageErrorHop;

  private SwtUniversalImage imageInfoHop;

  private SwtUniversalImage imageWarning;

  private Image imageVersionBrowser;

  private Image imageNew;

  private SwtUniversalImage imageEdit;

  private Image imageDelete;

  private Image imageShowDeleted;

  private Image imagePauseLog;

  private Image imageContinueLog;

  private Image imageImport;

  private Image imageExport;

  private SwtUniversalImage imageHopInput;

  private SwtUniversalImage imageHopOutput;

  private SwtUniversalImage imageHopTarget;

  private SwtUniversalImage imageLocked;

  private SwtUniversalImage imageTrue;

  private SwtUniversalImage imageFalse;

  private SwtUniversalImage imageContextMenu;

  private SwtUniversalImage imageUnconditionalHop;

  private SwtUniversalImage imageParallelHop;

  private SwtUniversalImage imageBusy;

  private SwtUniversalImage imageInject;

  private SwtUniversalImage imageBalance;

  private SwtUniversalImage imageCheckpoint;

  private Image imageGantt;

  private Image imageHelpWeb;

  /**
   * Same result as <code>new Image(display, 16, 16)</code>.
   */
  private Image imageEmpty16x16;

  private Map<String, Image> imageMap;

  private Map<RGB, Color> colorMap;

  private Image imageSpoonHigh;

  private Image imageHadoop;

  private Image imageDropHere;

  private Image imageAddAll;

  private Image imageAddSingle;

  private Image imageRemoveAll;

  private Image imageRemoveSingle;


  /**
   * GUIResource also contains the clipboard as it has to be allocated only once! I don't want to put it in a separate
   * singleton just for this one member.
   */
  private static Clipboard clipboard;

  private GUIResource( Display display ) {
    this.display = display;

    getResources();

    display.addListener( SWT.Dispose, new Listener() {
      public void handleEvent( Event event ) {
        dispose( false );
      }
    } );

    clipboard = null;

    // Reload images as required by changes in the plugins
    PluginRegistry.getInstance().addPluginListener( StepPluginType.class, new PluginTypeListener() {
      @Override
      public void pluginAdded( Object serviceObject ) {
        loadStepImages();
      }

      @Override
      public void pluginRemoved( Object serviceObject ) {
        loadStepImages();
      }

      @Override
      public void pluginChanged( Object serviceObject ) {
      }
    } );
  }

  public static final GUIResource getInstance() {
    if ( guiResource != null ) {
      return guiResource;
    }
    guiResource = new GUIResource( PropsUI.getDisplay() );
    return guiResource;
  }

  /**
   * reloads all colors, fonts and images.
   */
  public void reload() {
    dispose( true );
    getResources();
  }

  private void getResources() {
    PropsUI props = PropsUI.getInstance();
    imageMap = new HashMap<String, Image>();
    colorMap = new HashMap<RGB, Color>();

    colorBackground = new ManagedColor( display, props.getBackgroundRGB() );
    colorGraph = new ManagedColor( display, props.getGraphColorRGB() );
    colorTab = new ManagedColor( display, props.getTabColorRGB() );
    colorSuccessGreen = new ManagedColor( display, 0, 139, 0 );
    colorRed = new ManagedColor( display, 255, 0, 0 );
    colorGreen = new ManagedColor( display, 0, 255, 0 );
    colorBlue = new ManagedColor( display, 0, 0, 255 );
    colorYellow = new ManagedColor( display, 255, 255, 0 );
    colorMagenta = new ManagedColor( display, 255, 0, 255 );
    colorOrange = new ManagedColor( display, 255, 165, 0 );

    colorBlueCustomGrid = new ManagedColor( display, 240, 248, 255 );

    colorWhite = new ManagedColor( display, 255, 255, 255 );
    colorDemoGray = new ManagedColor( display, 240, 240, 240 );
    colorLightGray = new ManagedColor( display, 225, 225, 225 );
    colorGray = new ManagedColor( display, 215, 215, 215 );
    colorDarkGray = new ManagedColor( display, 100, 100, 100 );
    colorBlack = new ManagedColor( display, 0, 0, 0 );
    colorLightBlue = new ManagedColor( display, 135, 206, 250 ); // light sky blue

    colorDirectory = new ManagedColor( display, 0, 0, 255 );
    // colorPentaho = new ManagedColor(display, 239, 128, 51 ); // Orange
    colorPentaho = new ManagedColor( display, 188, 198, 82 );
    colorLightPentaho = new ManagedColor( display, 238, 248, 152 );
    colorCreamPentaho = new ManagedColor( display, 248, 246, 231 );

    colorCrystalTextPentaho = new ManagedColor( display, 61, 99, 128 );

    // Load all images from files...
    loadFonts();
    loadCommonImages();
    loadStepImages();
    loadJobEntryImages();
  }

  private void dispose( boolean reload ) {
    // Colors
    colorBackground.dispose();
    colorGraph.dispose();
    colorTab.dispose();

    colorRed.dispose();
    colorSuccessGreen.dispose();
    colorGreen.dispose();
    colorBlue.dispose();
    colorGray.dispose();
    colorYellow.dispose();
    colorMagenta.dispose();
    colorOrange.dispose();
    colorBlueCustomGrid.dispose();

    colorWhite.dispose();
    colorDemoGray.dispose();
    colorLightGray.dispose();
    colorDarkGray.dispose();
    colorBlack.dispose();
    colorLightBlue.dispose();

    colorDirectory.dispose();
    colorPentaho.dispose();
    colorLightPentaho.dispose();
    colorCreamPentaho.dispose();

    disposeColors( colorMap.values() );

    if ( !reload ) {
      // display shutdown, clean up our mess

      // Fonts
      fontGraph.dispose();
      fontNote.dispose();
      fontFixed.dispose();
      fontMedium.dispose();
      fontMediumBold.dispose();
      fontLarge.dispose();
      fontTiny.dispose();
      fontSmall.dispose();
      fontBold.dispose();

      // Common images
      imageHop.dispose();
      imageDisabledHop.dispose();
      imageConnection.dispose();
      imageAdd.dispose();
      imageTable.dispose();
      imageSchema.dispose();
      imageSynonym.dispose();
      imageProcedure.dispose();
      imageExploreDbSmall.dispose();
      imageView.dispose();
      imageLogoSmall.dispose();
      imageKettleLogo.dispose();
      imageBanner.dispose();
      imageBol.dispose();
      imageCalendar.dispose();
      imageCluster.dispose();
      imageSlave.dispose();
      imageArrow.dispose();
      imageFolder.dispose();
      imageWizard.dispose();
      imageCredits.dispose();
      imageStart.dispose();
      imageDummy.dispose();
      imageSpoon.dispose();
      imageJob.dispose();
      imagePentaho.dispose();
      imagePentahoSwirl.dispose();
      imageVariable.dispose();
      imageTransGraph.dispose();
      imageJobGraph.dispose();
      imageUser.dispose();
      imageProfil.dispose();
      imageFolderConnections.dispose();
      imageShowResults.dispose();
      imageHideResults.dispose();
      imageCollapseAll.dispose();
      imageStepError.dispose();
      imageCopyHop.dispose();
      imageErrorHop.dispose();
      imageInfoHop.dispose();
      imageWarning.dispose();
      imageVersionBrowser.dispose();
      imageExpandAll.dispose();
      imageSearchSmall.dispose();
      imageRegExSmall.dispose();
      imageViewPanel.dispose();
      imageDesignPanel.dispose();
      imageNew.dispose();
      imageEdit.dispose();
      imageDelete.dispose();
      imageShowDeleted.dispose();
      imagePauseLog.dispose();
      imageContinueLog.dispose();
      imageLocked.dispose();
      imageImport.dispose();
      imageExport.dispose();
      imageHopInput.dispose();
      imageHopOutput.dispose();
      imageHopTarget.dispose();
      imageKeySmall.dispose();
      imageColumnSmall.dispose();
      imageTrue.dispose();
      imageFalse.dispose();
      imageContextMenu.dispose();
      imageParallelHop.dispose();
      imageUnconditionalHop.dispose();
      imageBusy.dispose();
      imageEmpty16x16.dispose();
      imageInject.dispose();
      imageBalance.dispose();
      imageCheckpoint.dispose();
      imageGantt.dispose();
      imageHelpWeb.dispose();
      imageHadoop.dispose();
      imageDropHere.dispose();
      imageAddAll.dispose();
      imageAddSingle.dispose();
      imageRemoveAll.dispose();
      imageRemoveSingle.dispose();

      disposeImage( imageNoteSmall );
      disposeImage( imageColor );
      disposeImage( imageEditOptionButton );
      disposeImage( imageResetOptionButton );

      disposeImage( imageEditSmall );
      disposeImage( imageExploreSolutionSmall );

      disposeImage( imageShowLog );
      disposeImage( imageShowGrid );
      disposeImage( imageShowHistory );
      disposeImage( imageShowPerf );

      disposeImage( imageShowInactive );
      disposeImage( imageHideInactive );

      disposeImage( imageShowSelected );
      disposeImage( imageShowAll );

      disposeImage( imageClosePanel );
      disposeImage( imageMaximizePanel );
      disposeImage( imageMinimizePanel );

      disposeImage( imageShowErrorLines );

      // big images
      disposeUniversalImages( imagesSteps.values() );

      // Small images
      disposeImages( imagesStepsSmall.values() );

      // Dispose of the images in the map
      disposeImages( imageMap.values() );
    }
  }

  private void disposeImages( Collection<Image> c ) {
    for ( Image image : c ) {
      disposeImage( image );
    }
  }

  private void disposeUniversalImages( Collection<SwtUniversalImage> c ) {
    for ( SwtUniversalImage image : c ) {
      image.dispose();
    }
  }

  private void disposeColors( Collection<Color> colors ) {
    for ( Color color : colors ) {
      color.dispose();
    }
  }

  private void disposeImage( Image image ) {
    if ( image != null && !image.isDisposed() ) {
      image.dispose();
    }
  }

  /**
   * Load all step images from files.
   *
   */
  private void loadStepImages() {
    // imagesSteps.clear();
    // imagesStepsSmall.clear();

    //
    // STEP IMAGES TO LOAD
    //
    PluginRegistry registry = PluginRegistry.getInstance();

    List<PluginInterface> steps = registry.getPlugins( StepPluginType.class );
    for ( int i = 0; i < steps.size(); i++ ) {
      if ( imagesSteps.get( steps.get( i ).getIds()[0] ) != null ) {
        continue;
      }

      SwtUniversalImage image = null;
      Image small_image = null;

      String filename = steps.get( i ).getImageFile();
      try {
        ClassLoader classLoader = registry.getClassLoader( steps.get( i ) );
        image = SwtSvgImageUtil.getUniversalImage( display, classLoader, filename );
      } catch ( Exception e ) {
        log.logError( "Unable to find required step image file or image format not supported (e.g. interlaced) ["
            + filename + " : ", e );
        Image img = new Image( display, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
        GC gc = new GC( img );
        gc.drawRectangle( 0, 0, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
        gc.drawLine( 0, 0, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
        gc.drawLine( ConstUI.ICON_SIZE, 0, 0, ConstUI.ICON_SIZE );
        gc.dispose();
        image = new SwtUniversalImage( img );
      }

      // Calculate the smaller version of the image @ 16x16...
      // Perhaps we should make this configurable?
      //
      if ( image != null ) {
        small_image = image.getAsBitmapForSize( display, ConstUI.MEDIUM_ICON_SIZE, ConstUI.MEDIUM_ICON_SIZE );
      }

      imagesSteps.put( steps.get( i ).getIds()[0], image );
      imagesStepsSmall.put( steps.get( i ).getIds()[0], small_image );
    }
  }

  private void loadFonts() {
    PropsUI props = PropsUI.getInstance();

    fontGraph = new ManagedFont( display, props.getGraphFont() );
    fontNote = new ManagedFont( display, props.getNoteFont() );
    fontFixed = new ManagedFont( display, props.getFixedFont() );

    // Create a medium size version of the graph font
    FontData mediumFontData =
      new FontData(
        props.getGraphFont().getName(), (int) Math.round( props.getGraphFont().getHeight() * 1.2 ), props
          .getGraphFont().getStyle() );
    fontMedium = new ManagedFont( display, mediumFontData );

    // Create a medium bold size version of the graph font
    FontData mediumFontBoldData =
      new FontData(
        props.getGraphFont().getName(), (int) Math.round( props.getGraphFont().getHeight() * 1.2 ), props
          .getGraphFont().getStyle()
          | SWT.BOLD );
    fontMediumBold = new ManagedFont( display, mediumFontBoldData );

    // Create a large version of the graph font
    FontData largeFontData =
      new FontData( props.getGraphFont().getName(), props.getGraphFont().getHeight() * 3, props
        .getGraphFont().getStyle() );
    fontLarge = new ManagedFont( display, largeFontData );

    // Create a tiny version of the graph font
    FontData tinyFontData =
      new FontData( props.getGraphFont().getName(), props.getGraphFont().getHeight() - 2, props
        .getGraphFont().getStyle() );
    fontTiny = new ManagedFont( display, tinyFontData );

    // Create a small version of the graph font
    FontData smallFontData =
      new FontData( props.getGraphFont().getName(), props.getGraphFont().getHeight() - 1, props
        .getGraphFont().getStyle() );
    fontSmall = new ManagedFont( display, smallFontData );

    FontData boldFontData =
      new FontData( props.getDefaultFontData().getName(), props.getDefaultFontData().getHeight(), props
        .getDefaultFontData().getStyle()
        | SWT.BOLD );
    fontBold = new ManagedFont( display, boldFontData );
  }

  private void loadCommonImages() {
    // "ui/images/HOP.png"
    imageHop = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HOP_image" ) );

    // "ui/images/CNC.png"
    imageConnection = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "CNC_image" ) );

    // "ui/images/Add.png"
    imageAdd = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Add_image" ) );

    imageDisabledHop =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "Disabled_HOP_image" ) ), new RGB( 255, 255, 255 ) );

    // "ui/images/table.png"
    imageTable = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Table_image" ) );

    // "ui/images/schema.png"
    imageSchema = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Schema_image" ) );

    // "ui/images/synonym.png"
    imageSynonym = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Synonym_image" ) );

    // "ui/images/view.png"
    imageView = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "View_image" ) );

    // "ui/images/color.png.png"
    imageColor = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Color_image" ) );

    // "ui/images/noteSmall.png"
    imageNoteSmall = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Note_image" ) );

    // "ui/images/proc.png"
    imageProcedure =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ProcedureSmall_image" ) );

    // , "ui/images/exploreDbSmall.png"
    imageExploreDbSmall =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "ExploreDbSmall_image" ) ), new RGB( 255, 255, 255 ) );

    // "ui/images/cluster.png"
    imageCluster = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Cluster_image" ) );

    // , "ui/images/slave.png"
    imageSlave =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "Slave_image" ) ), new RGB( 255, 255, 255 ) );

    // "ui/images/logo_kettle_lrg.png"
    imageKettleLogo = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Logo_lrg_image" ) );
    // "ui/images/bg_banner.png"
    imageBanner = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Banner_bg_image" ) );

    // "ui/images/BOL.png"
    imageBol = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "BOL_image" ) ) );
    
    imageCalendar =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "Calendar_image" ) ), new RGB( 255, 255, 255 ) ); // , "ui/images/Calendar.png"

    // "ui/images/credits.png"
    imageCredits = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Credits_image" ) );

    // "ui/images/STR.png"
    imageStart = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "STR_image" ) ) );

    // "ui/images/DUM.png"
    imageDummy = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "DUM_image" ) ) );

    // "ui/images/spoon.ico"
    imageSpoon = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "spoon_image" ) );

    // "ui/images/spoon_highres.png"
    imageSpoonHigh = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "spoon_image_high" ) );

    // "ui/images/chef.png"
    imageJob = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Chef_image" ) );

    // "ui/images/PentahoLogo.png"
    imagePentaho = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "CorpLogo_image" ) );

    // "ui/images/pentaho-swirl.png"
    imagePentahoSwirl =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "CorpSwirl_image" ) );

    // "ui/images/variable.png"
    imageVariable = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Variable_image" ) );

    // "ui/images/edit_option.png"
    imageEditOptionButton =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "EditOption_image" ) );

    // "ui/images/reset_option.png"
    imageResetOptionButton =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ResetOption_image" ) );

    // "ui/images/Edit.png"
    imageEditSmall = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "EditSmall_image" ) );

    // "ui/images/exploreSolution.png"
    imageExploreSolutionSmall =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ExploreSolutionSmall_image" ) );

    // "ui/images/show-log.png"
    imageShowLog = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowLog_image" ) );

    // "ui/images/show-grid.png"
    imageShowGrid = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowGrid_image" ) );

    // "ui/images/show-history.png"
    imageShowHistory =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowHistory_image" ) );

    // "ui/images/show-perf.png"
    imageShowPerf = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowPerf_image" ) );

    // ui/images/show-inactive-selected.png
    imageShowInactive =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowInactive_image" ) );

    // ui/images/show-inactive-selected.png
    imageHideInactive =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HideInactive_image" ) );

    // ui/images/show-selected.png
    imageShowSelected =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowSelected_image" ) );

    // ui/images/show-all.png
    imageShowAll = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowAll_image" ) );

    // "ui/images/show-perf.png"
    imageClosePanel =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ClosePanel_image" ) );

    // "ui/images/show-perf.png"
    imageMaximizePanel =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "MaximizePanel_image" ) );

    // "ui/images/show-perf.png"
    imageMinimizePanel =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "MinimizePanel_image" ) );

    // "ui/images/show-perf.png"
    imageShowErrorLines =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowErrorLines_image" ) );

    // "ui/images/show-results.png
    imageShowResults =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowResults_image" ) );

    // "ui/images/hide-results.png
    imageHideResults =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HideResults_image" ) );

    // "ui/images/Design.png;
    imageDesignPanel =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "DesignPanel_image" ) );

    // "ui/images/View.png;
    imageViewPanel = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ViewPanel_image" ) );

    // "ui/images/ExpandAll.png;
    imageExpandAll = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ExpandAll_image" ) );

    // "ui/images/CollapseAll.png;
    imageCollapseAll =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "CollapseAll_image" ) );

    // "ui/images/show-error-lines.png;
    imageStepError =
        SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "StepErrorLines_image" ) );

    // "ui/images/copy-hop.png;
    imageCopyHop = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "CopyHop_image" ) );

    // "ui/images/error-hop.png;
    imageErrorHop = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ErrorHop_image" ) );

    // "ui/images/info-hop.png;
    imageInfoHop = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "InfoHop_image" ) );

    // "ui/images/warning.png;
    imageWarning = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Warning_image" ) );

    // "ui/images/version-history.png;
    imageVersionBrowser =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "VersionBrowser_image" ) );

    // "ui/images/generic-new.png;
    imageNew = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "New_image" ) );

    // "ui/images/generic-edit.png;
    imageEdit = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Edit_image" ) );

    // "ui/images/generic-delete.png;
    imageDelete = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Delete_image" ) );

    // "ui/images/show-deleted.png;
    imageShowDeleted =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ShowDeleted_image" ) );

    // "ui/images/pause-log.png;
    imagePauseLog = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "PauseLog_image" ) );

    // "ui/images/continue-log.png;
    imageContinueLog =
      ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ContinueLog_image" ) );

    // "ui/images/import.png;
    imageImport = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Import_image" ) );

    // "ui/images/export.png;
    imageExport = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Export_image" ) );

    // "ui/images/hop-input.png;
    imageHopInput = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HopInput_image" ) );

    // "ui/images/hop-output.png;
    imageHopOutput = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HopOutput_image" ) );

    // "ui/images/hop-target.png;
    imageHopTarget = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HopTarget_image" ) );

    // "ui/images/locked.png;
    imageLocked = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Locked_image" ) );

    // "ui/images/true.png;
    imageTrue = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "True_image" ) );

    // "ui/images/false.png;
    imageFalse = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "False_image" ) );

    // "ui/images/context_menu.png;
    imageContextMenu =
        SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ContextMenu_image" ) );

    // "ui/images/parallel-hop.png
    imageParallelHop =
        SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "ParallelHop_image" ) );

    // "ui/images/unconditional-hop.png
    imageUnconditionalHop =
        SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "UnconditionalHop_image" ) );

    // "ui/images/busy.png
    imageBusy = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Busy_image" ) );

    // "ui/images/inject.png
    imageInject = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Inject_image" ) );

    // "ui/images/scales.png
    imageBalance = SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "LoadBalance_image" ) );

    // "ui/images/scales.png
    imageCheckpoint =
        SwtSvgImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "CheckeredFlag_image" ) );

    // "ui/images/gantt.png
    imageGantt = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Gantt_image" ) );

    // "ui/images/help_web.png
    imageHelpWeb = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "HelpWeb_image" ) );

    // "ui/images/hadoop.png
    imageHadoop = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Hadoop_image" ) );

    // "ui/images/drop_here.png
    imageDropHere = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "DropHere_image" ) );

    // "ui/images/add_all.png
    imageAddAll = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "AddAll_image" ) );

    // "ui/images/add_single.png
    imageAddSingle = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "AddSingle_image" ) );

    // "ui/images/remove_all.png
    imageRemoveAll = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "RemoveAll_image" ) );

    // "ui/images/remove_single.png
    imageRemoveSingle = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "RemoveSingle_image" ) );

    imageEmpty16x16 = new Image( display, 16, 16 );

    imageTransGraph = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "SpoonIcon_image" ) ) );
    imageJobGraph = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "ChefIcon_image" ) ) );

    // "ui/images/kettle_logo_small.png"
    imageLogoSmall = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "Logo_sml_image" ) ) );
    
    // "ui/images/arrow.png"
    imageArrow = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), SvgSupport.toSvgName( BasePropertyHandler.getProperty( "ArrowIcon_image" ) ) );

    // "ui/images/folder.png"
    imageFolder = SwtSvgImageUtil.getUniversalImage( 
        display, getClass().getClassLoader(), ( "ui/images/folder.svg" ) );
    
    // Makes transparent images "on the fly"
    //

    // "ui/images/wizard.png"
    imageWizard = ImageUtil.getImageAsResource( display, BasePropertyHandler.getProperty( "Wizard_image" ) );

    imageBanner =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "Banner_bg_image" ) ), // , "ui/images/bg_banner.png"
        new RGB( 255, 255, 255 ) );

    imageUser =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "User_image" ) ), // , "ui/images/user.png"
        new RGB( 255, 255, 255 ) );
    imageProfil =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "Profil_image" ) ), // , "ui/images/profil.png"
        new RGB( 255, 255, 255 ) );

    imageFolderConnections =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "FolderConnections_image" ) ), // , "ui/images/folder_connection.png"
        new RGB( 255, 255, 255 ) );

    imageRegExSmall =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "RegExSmall_image" ) ), new RGB( 255, 255, 255 ) );

    imageSearchSmall =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "SearchSmall_image" ) ), new RGB( 255, 255, 255 ) );
    imageKeySmall =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "KeySmall_image" ) ), new RGB( 255, 255, 255 ) );

    imageColumnSmall =
      ImageUtil.makeImageTransparent( display, ImageUtil.getImageAsResource( display, BasePropertyHandler
        .getProperty( "ColumnSmall_image" ) ), new RGB( 255, 255, 255 ) );

  }

  /**
   * Load all step images from files.
   *
   */
  private void loadJobEntryImages() {
    imagesJobentries = new Hashtable<String, SwtUniversalImage>();
    imagesJobentriesSmall = new Hashtable<String, Image>();

    // //
    // // JOB ENTRY IMAGES TO LOAD
    // //
    PluginRegistry registry = PluginRegistry.getInstance();

    List<PluginInterface> plugins = registry.getPlugins( JobEntryPluginType.class );
    for ( int i = 0; i < plugins.size(); i++ ) {
      PluginInterface plugin = plugins.get( i );

      if ( "SPECIAL".equals( plugin.getIds()[0] ) ) {
        continue;
      }

      SwtUniversalImage image = null;
      Image small_image = null;

      String filename = plugin.getImageFile();
      try {
        ClassLoader classLoader = registry.getClassLoader( plugin );
        image = SwtSvgImageUtil.getUniversalImage( display, classLoader, filename );
      } catch ( Exception e ) {
        log.logError( "Unable to find required job entry image file ["
          + filename + "] for id [" + plugin.getIds()[0] + "] : " + e.toString() );
        Image img = new Image( display, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
        GC gc = new GC( img );
        gc.drawRectangle( 0, 0, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
        gc.drawLine( 0, 0, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
        gc.drawLine( ConstUI.ICON_SIZE, 0, 0, ConstUI.ICON_SIZE );
        gc.dispose();
        image = new SwtUniversalImage( img );
      }

      // Calculate the smaller version of the image @ 16x16...
      // Perhaps we should make this configurable?
      //
      if ( image != null ) {
        small_image = image.getAsBitmapForSize( display, ConstUI.MEDIUM_ICON_SIZE, ConstUI.MEDIUM_ICON_SIZE );
      }

      imagesJobentries.put( plugin.getIds()[0], image );
      imagesJobentriesSmall.put( plugin.getIds()[0], small_image );
    }
  }

  /**
   * @return Returns the colorBackground.
   */
  public Color getColorBackground() {
    return colorBackground.getColor();
  }

  /**
   * @return Returns the colorBlack.
   */
  public Color getColorBlack() {
    return colorBlack.getColor();
  }

  /**
   * @return Returns the colorBlue.
   */
  public Color getColorBlue() {
    return colorBlue.getColor();
  }

  /**
   * @return Returns the colorDarkGray.
   */
  public Color getColorDarkGray() {
    return colorDarkGray.getColor();
  }

  /**
   * @return Returns the colorDemoGray.
   */
  public Color getColorDemoGray() {
    return colorDemoGray.getColor();
  }

  /**
   * @return Returns the colorDirectory.
   */
  public Color getColorDirectory() {
    return colorDirectory.getColor();
  }

  /**
   * @return Returns the colorGraph.
   */
  public Color getColorGraph() {
    return colorGraph.getColor();
  }

  /**
   * @return Returns the colorGray.
   */
  public Color getColorGray() {
    return colorGray.getColor();
  }

  /**
   * @return Returns the colorGreen.
   */
  public Color getColorGreen() {
    return colorGreen.getColor();
  }

  /**
   * @return Returns the colorLightGray.
   */
  public Color getColorLightGray() {
    return colorLightGray.getColor();
  }

  /**
   * @return Returns the colorLightBlue.
   */
  public Color getColorLightBlue() {
    return colorLightBlue.getColor();
  }

  /**
   * @return Returns the colorMagenta.
   */
  public Color getColorMagenta() {
    return colorMagenta.getColor();
  }

  /**
   * @return Returns the colorOrange.
   */
  public Color getColorOrange() {
    return colorOrange.getColor();
  }

  /**
   * @return Returns the colorSuccessGreen.
   */
  public Color getColorSuccessGreen() {
    return colorSuccessGreen.getColor();
  }

  /**
   * @return Returns the colorRed.
   */
  public Color getColorRed() {
    return colorRed.getColor();
  }

  /**
   * @return Returns the colorBlueCustomGrid.
   */
  public Color getColorBlueCustomGrid() {
    return colorBlueCustomGrid.getColor();
  }

  /**
   * @return Returns the colorTab.
   */
  public Color getColorTab() {
    return colorTab.getColor();
  }

  /**
   * @return Returns the colorWhite.
   */
  public Color getColorWhite() {
    return colorWhite.getColor();
  }

  /**
   * @return Returns the colorYellow.
   */
  public Color getColorYellow() {
    return colorYellow.getColor();
  }

  /**
   * @return Returns the display.
   */
  public Display getDisplay() {
    return display;
  }

  /**
   * @return Returns the fontFixed.
   */
  public Font getFontFixed() {
    return fontFixed.getFont();
  }

  /**
   * @return Returns the fontGraph.
   */
  public Font getFontGraph() {
    return fontGraph.getFont();
  }

  /**
   * @return Returns the fontNote.
   */
  public Font getFontNote() {
    return fontNote.getFont();
  }

  /**
   * @return Returns the imageBol.
   */
  public Image getImageBol() {
    return imageBol.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  /**
   * @return Returns the imageCalendar.
   */
  public Image getImageCalendar() {
    return imageCalendar;
  }

  /**
   * @return Returns the imageCluster.
   */
  public Image getImageCluster() {
    return imageCluster;
  }

  /**
   * @return Returns the imageSlave.
   */
  public Image getImageSlave() {
    return imageSlave;
  }

  /**
   * @return Returns the imageConnection.
   */
  public Image getImageConnection() {
    return imageConnection.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageConnection() {
    return imageConnection;
  }

  public Image getImageAdd() {
    return imageAdd;
  }

  /**
   * @return Returns the imageTable.
   */
  public Image getImageTable() {
    return imageTable;
  }

  /**
   * @return Returns the imageKeySmall.
   */
  public Image getImageKeySmall() {
    return imageKeySmall;
  }

  /**
   * @return Returns the imageColumnSmall.
   */
  public Image getImageColumnSmall() {
    return imageColumnSmall;
  }

  /**
   * @return Returns the imageSchema.
   */
  public Image getImageSchema() {
    return imageSchema;
  }

  /**
   * @return Returns the imageSynonym.
   */
  public Image getImageSynonym() {
    return imageSynonym;
  }

  /**
   * @return Returns the imageProcedure.
   */
  public Image getImageProcedure() {
    return imageProcedure;
  }

  /**
   * @return Returns the imageExploreDbSmall.
   */
  public Image getImageExploreDbSmall() {
    return imageExploreDbSmall;
  }

  /**
   * @return Returns the imageView.
   */
  public Image getImageView() {
    return imageView;
  }

  /**
   * @return Returns the imageView.
   */
  public Image getImageNoteSmall() {
    return imageNoteSmall;
  }

  /**
   * @return Returns the imageColor.
   */
  public Image getImageColor() {
    return imageColor;
  }

  /**
   * @return Returns the imageCredits.
   */
  public Image getImageCredits() {
    return imageCredits;
  }

  /**
   * @return Returns the imageDummy.
   */
  public Image getImageDummy() {
    return imageDummy.getAsBitmapForSize( display, 32, 32 );
  }

  public SwtUniversalImage getSwtImageDummy() {
    return imageDummy;
  }
  
  /**
   * @return Returns the imageHop.
   */
  public Image getImageHop() {
    return imageHop;
  }

  /**
   * @return Returns the imageDisabledHop.
   */
  public Image getImageDisabledHop() {
    return imageDisabledHop;
  }

  /**
   * @return Returns the imageSpoon.
   */
  public Image getImageSpoon() {
    return imageSpoon;
  }

  /**
   * @return Returns the image Pentaho.
   */
  public Image getImagePentaho() {
    return imagePentaho;
  }

  /**
   * @return Returns the Pentaho swirl image (64x64).
   */
  public Image getImagePentahoSwirl() {
    return imagePentahoSwirl;
  }

  /**
   * @return Returns the imagesSteps.
   */
  public Map<String, SwtUniversalImage> getImagesSteps() {
    return imagesSteps;
  }

  /**
   * @return Returns the imagesStepsSmall.
   */
  public Map<String, Image> getImagesStepsSmall() {
    return imagesStepsSmall;
  }

  /**
   * @return Returns the imageStart.
   */
  public Image getImageStart() {
    return imageStart.getAsBitmapForSize( display, 32, 32 );
  }

  public SwtUniversalImage getSwtImageStart() {
    return imageStart;
  }
  
  /**
   * @return Returns the imagesJobentries.
   */
  public Map<String, SwtUniversalImage> getImagesJobentries() {
    return imagesJobentries;
  }

  /**
   * @param imagesJobentries
   *          The imagesJobentries to set.
   */
  public void setImagesJobentries( Hashtable<String, SwtUniversalImage> imagesJobentries ) {
    this.imagesJobentries = imagesJobentries;
  }

  /**
   * @return Returns the imagesJobentriesSmall.
   */
  public Map<String, Image> getImagesJobentriesSmall() {
    return imagesJobentriesSmall;
  }

  /**
   * @param imagesJobentriesSmall
   *          The imagesJobentriesSmall to set.
   */
  public void setImagesJobentriesSmall( Hashtable<String, Image> imagesJobentriesSmall ) {
    this.imagesJobentriesSmall = imagesJobentriesSmall;
  }

  /**
   * @return Returns the imageChef.
   */
  public Image getImageChef() {
    return imageJob;
  }

  /**
   * @param imageChef
   *          The imageChef to set.
   */
  public void setImageChef( Image imageChef ) {
    this.imageJob = imageChef;
  }

  /**
   * @return the fontLarge
   */
  public Font getFontLarge() {
    return fontLarge.getFont();
  }

  /**
   * @return the tiny font
   */
  public Font getFontTiny() {
    return fontTiny.getFont();
  }

  /**
   * @return the small font
   */
  public Font getFontSmall() {
    return fontSmall.getFont();
  }

  /**
   * @return Returns the clipboard.
   */
  public Clipboard getNewClipboard() {
    if ( clipboard != null ) {
      clipboard.dispose();
      clipboard = null;
    }
    clipboard = new Clipboard( display );

    return clipboard;
  }

  public void toClipboard( String cliptext ) {
    if ( cliptext == null ) {
      return;
    }

    getNewClipboard();
    TextTransfer tran = TextTransfer.getInstance();
    clipboard.setContents( new String[] { cliptext }, new Transfer[] { tran } );
  }

  public String fromClipboard() {
    getNewClipboard();
    TextTransfer tran = TextTransfer.getInstance();

    return (String) clipboard.getContents( tran );
  }

  public Font getFontBold() {
    return fontBold.getFont();
  }

  /**
   * @return the imageVariable
   */
  public Image getImageVariable() {
    return imageVariable;
  }

  public Image getImageTransGraph() {
    return imageTransGraph.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  public Image getImageUser() {
    return imageUser;
  }

  public Image getImageProfil() {
    return imageProfil;
  }

  public Image getImageFolderConnections() {
    return imageFolderConnections;
  }

  public Image getImageJobGraph() {
    return imageJobGraph.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  public Image getEditOptionButton() {
    return imageEditOptionButton;
  }

  public Image getResetOptionButton() {
    return imageResetOptionButton;
  }

  public Image getImageEditSmall() {
    return imageEditSmall;
  }

  public Image getImageExploreSolutionSmall() {
    return imageExploreSolutionSmall;
  }

  /**
   * @return the imageArrow
   */
  public Image getImageArrow() {
    return imageArrow.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageArrow() {
    return imageArrow;
  }
  
  /**
   * @return the imageArrow
   */
  public Image getImageFolder() {
    return imageFolder.getAsBitmapForSize( display, ConstUI.MEDIUM_ICON_SIZE, ConstUI.MEDIUM_ICON_SIZE );
  }
  
  /**
   * @return the imageDummySmall
   */
  public Image getImageDummySmall() {
    return imageDummy.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  /**
   * @return the imageStartSmall
   */
  public Image getImageStartSmall() {
    return imageStart.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  /**
   * @return the imageDummyMedium
   */
  public Image getImageDummyMedium() {
    return imageDummy.getAsBitmapForSize( display, ConstUI.MEDIUM_ICON_SIZE, ConstUI.MEDIUM_ICON_SIZE );
  }

  /**
   * @return the imageStartSmall
   */
  public Image getImageStartMedium() {
    return imageStart.getAsBitmapForSize( display, ConstUI.MEDIUM_ICON_SIZE, ConstUI.MEDIUM_ICON_SIZE );
  }
  
  /**
   * @return the imageBanner
   */
  public Image getImageBanner() {
    return imageBanner;
  }

  /**
   * @return the imageWizard
   */
  public Image getImageWizard() {
    return imageWizard;
  }

  /**
   * @param imageBanner
   *          the imageBanner to set
   */
  public void setImageBanner( Image imageBanner ) {
    this.imageBanner = imageBanner;
  }

  /**
   * @return the imageKettleLogo
   */
  public Image getImageKettleLogo() {
    return imageKettleLogo;
  }

  /**
   * @param imageKettleLogo
   *          the imageKettleLogo to set
   */
  public void setImageKettleLogo( Image imageKettleLogo ) {
    this.imageKettleLogo = imageKettleLogo;
  }

  /**
   * @return the colorPentaho
   */
  public Color getColorPentaho() {
    return colorPentaho.getColor();
  }

  /**
   * @return the imageLogoSmall
   */
  public Image getImageLogoSmall() {
    return imageLogoSmall.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  /**
   * @return the colorLightPentaho
   */
  public Color getColorLightPentaho() {
    return colorLightPentaho.getColor();
  }

  /**
   * @return the colorCreamPentaho
   */
  public Color getColorCreamPentaho() {
    return colorCreamPentaho.getColor();
  }

  /**
   * @return the default color of text in the Pentaho Crystal theme
   */
  public Color getColorCrystalTextPentaho() {
    return colorCrystalTextPentaho.getColor();
  }

  public void drawPentahoGradient( Display display, GC gc, Rectangle rect, boolean vertical ) {
    if ( !vertical ) {
      gc.setForeground( display.getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
      gc.setBackground( GUIResource.getInstance().getColorPentaho() );
      gc.fillGradientRectangle( rect.x, rect.y, 2 * rect.width / 3, rect.height, vertical );
      gc.setForeground( GUIResource.getInstance().getColorPentaho() );
      gc.setBackground( display.getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
      gc.fillGradientRectangle( rect.x + 2 * rect.width / 3, rect.y, rect.width / 3 + 1, rect.height, vertical );
    } else {
      gc.setForeground( display.getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
      gc.setBackground( GUIResource.getInstance().getColorPentaho() );
      gc.fillGradientRectangle( rect.x, rect.y, rect.width, 2 * rect.height / 3, vertical );
      gc.setForeground( GUIResource.getInstance().getColorPentaho() );
      gc.setBackground( display.getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
      gc.fillGradientRectangle( rect.x, rect.y + 2 * rect.height / 3, rect.width, rect.height / 3 + 1, vertical );
    }
  }

  /**
   * Generic popup with a toggle option
   *
   * @param dialogTitle
   * @param image
   * @param message
   * @param dialogImageType
   * @param buttonLabels
   * @param defaultIndex
   * @param toggleMessage
   * @param toggleState
   * @return
   */
  public Object[] messageDialogWithToggle( Shell shell, String dialogTitle, Image image, String message,
    int dialogImageType, String[] buttonLabels, int defaultIndex, String toggleMessage, boolean toggleState ) {
    int imageType = 0;
    switch ( dialogImageType ) {
      case Const.WARNING:
        imageType = MessageDialog.WARNING;
        break;
      default:
        break;
    }

    MessageDialogWithToggle md =
      new MessageDialogWithToggle(
        shell, dialogTitle, image, message, imageType, buttonLabels, defaultIndex, toggleMessage, toggleState );
    int idx = md.open();
    return new Object[] { Integer.valueOf( idx ), Boolean.valueOf( md.getToggleState() ) };
  }

  public static Point calculateControlPosition( Control control ) {
    // Calculate the exact location...
    //
    Rectangle r = control.getBounds();
    Point p = control.getParent().toDisplay( r.x, r.y );

    return p;

    /*
     * Point location = control.getLocation();
     *
     * Composite parent = control.getParent(); while (parent!=null) {
     *
     * Composite newParent = parent.getParent(); if (newParent!=null) { location.x+=parent.getLocation().x;
     * location.y+=parent.getLocation().y; } else { if (parent instanceof Shell) { // Top level shell. Shell shell =
     * (Shell)parent; Rectangle bounds = shell.getBounds(); Rectangle clientArea = shell.getClientArea(); location.x +=
     * bounds.width-clientArea.width; location.y += bounds.height-clientArea.height; } } parent = newParent; }
     *
     * return location;
     */
  }

  /**
   * @return the fontMedium
   */
  public Font getFontMedium() {
    return fontMedium.getFont();
  }

  /**
   * @return the fontMediumBold
   */
  public Font getFontMediumBold() {
    return fontMediumBold.getFont();
  }

  /**
   * @return the imageShowLog
   */
  public Image getImageShowLog() {
    return imageShowLog;
  }

  /**
   * @return the imageShowGrid
   */
  public Image getImageShowGrid() {
    return imageShowGrid;
  }

  /**
   * @return the imageShowHistory
   */
  public Image getImageShowHistory() {
    return imageShowHistory;
  }

  /**
   * @return the imageShowPerf
   */
  public Image getImageShowPerf() {
    return imageShowPerf;
  }

  /**
   * @return the "hide inactive" image
   */
  public Image getImageHideInactive() {
    return imageHideInactive;
  }

  /**
   * @return the "show inactive" image
   */
  public Image getImageShowInactive() {
    return imageShowInactive;
  }

  /**
   * @return the "show selected" image
   */
  public Image getImageShowSelected() {
    return imageShowSelected;
  }

  /**
   * @return the "show all" image
   */
  public Image getImageShowAll() {
    return imageShowAll;
  }

  /**
   * @return the close panel image
   */
  public Image getImageClosePanel() {
    return imageClosePanel;
  }

  /**
   * @return the maximize panel image
   */
  public Image getImageMaximizePanel() {
    return imageMaximizePanel;
  }

  /**
   * @return the minimize panel image
   */
  public Image getImageMinimizePanel() {
    return imageMinimizePanel;
  }

  /**
   * @return the show error lines image
   */
  public Image getImageShowErrorLines() {
    return imageShowErrorLines;
  }

  public Image getImageShowResults() {
    return imageShowResults;
  }

  public Image getImageHideResults() {
    return imageHideResults;
  }

  public Image getImageDesignPanel() {
    return imageDesignPanel;
  }

  public Image getImageViewPanel() {
    return imageViewPanel;
  }

  public Image getImageExpandAll() {
    return imageExpandAll;
  }

  public Image getImageSearchSmall() {
    return imageSearchSmall;
  }

  public Image getImageRegexSmall() {
    return imageRegExSmall;
  }

  public Image getImageCollapseAll() {
    return imageCollapseAll;
  }

  public Image getImageStepError() {
    return imageStepError.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageStepError() {
    return imageStepError;
  }

  public Image getImageCopyHop() {
    return imageCopyHop.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageCopyHop() {
    return imageCopyHop;
  }

  public Image getImageErrorHop() {
    return imageErrorHop.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageErrorHop() {
    return imageErrorHop;
  }

  public Image getImageInfoHop() {
    return imageInfoHop.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageInfoHop() {
    return imageInfoHop;
  }

  public Image getImageWarning() {
    return imageWarning.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }

  public Image getImageWarning32() {
    return imageInfoHop.getAsBitmapForSize( display, ConstUI.ICON_SIZE, ConstUI.ICON_SIZE );
  }

  public Image getImageVersionBrowser() {
    return imageVersionBrowser;
  }

  public Image getImageNew() {
    return imageNew;
  }

  public Image getImageEdit() {
    return imageEdit.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageEdit() {
    return imageEdit;
  }

  public Image getImageDelete() {
    return imageDelete;
  }

  public Image getImageShowDeleted() {
    return imageShowDeleted;
  }

  public Image getImagePauseLog() {
    return imagePauseLog;
  }

  public Image getImageContinueLog() {
    return imageContinueLog;
  }

  public Image getImageImport() {
    return imageImport;
  }

  public Image getImageExport() {
    return imageExport;
  }

  public Image getImageHopInput() {
    return imageHopInput.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageHopInput() {
    return imageHopInput;
  }

  public Image getImageHopOutput() {
    return imageHopOutput.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageHopOutput() {
    return imageHopOutput;
  }

  public Image getImageHopTarget() {
    return imageHopTarget.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageHopTarget() {
    return imageHopTarget;
  }

  public Image getImageLocked() {
    return imageLocked.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageLocked() {
    return imageLocked;
  }

  /**
   * Loads an image from a location once. The second time, the image comes from a cache. Because of this, it's important
   * to never dispose of the image you get from here. (easy!) The images are automatically disposed when the application
   * ends.
   *
   * @param location
   * @return
   */
  public Image getImage( String location ) {
    Image image = imageMap.get( location );
    if ( image == null ) {
      image = ImageUtil.getImage( display, location );
      imageMap.put( location, image );
    }
    return image;
  }

  public Color getColor( int red, int green, int blue ) {
    RGB rgb = new RGB( red, green, blue );
    Color color = colorMap.get( rgb );
    if ( color == null ) {
      color = new Color( display, rgb );
      colorMap.put( rgb, color );
    }
    return color;
  }

  /**
   * @return The image map used to cache images loaded from certain location using getImage(String location);
   */
  public Map<String, Image> getImageMap() {
    return imageMap;
  }

  /**
   * @return the imageTrue
   */
  public Image getImageTrue() {
    return imageTrue.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageTrue() {
    return imageTrue;
  }
  
  /**
   * @return the imageFalse
   */
  public Image getImageFalse() {
    return imageFalse.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageFalse() {
    return imageFalse;
  }

  /**
   * @return the imageContextMenu
   */
  public Image getImageContextMenu() {
    return imageContextMenu.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageContextMenu() {
    return imageContextMenu;
  }

  public Image getImageParallelHop() {
    return imageParallelHop.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageParallelHop() {
    return imageParallelHop;
  }

  public Image getImageUnconditionalHop() {
    return imageUnconditionalHop.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageUnconditionalHop() {
    return imageUnconditionalHop;
  }
  
  public Image getImageBusy() {
    return imageBusy.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageBusy() {
    return imageBusy;
  }
  
  public Image getImageEmpty16x16() {
    return imageEmpty16x16;
  }

  public Image getImageInject() {
    return imageInject.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageInject() {
    return imageInject;
  }

  public Image getImageBalance() {
    return imageBalance.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageBalance() {
    return imageBalance;
  }

  public Image getImageSpoonHigh() {
    return imageSpoonHigh;
  }

  public void setImageSpoonHigh( Image imageSpoonHigh ) {
    this.imageSpoonHigh = imageSpoonHigh;
  }

  public Image getImageCheckpoint() {
    return imageCheckpoint.getAsBitmapForSize( display, ConstUI.SMALL_ICON_SIZE, ConstUI.SMALL_ICON_SIZE );
  }
  
  public SwtUniversalImage getSwtImageCheckpoint() {
    return imageCheckpoint;
  }

  public Image getImageGantt() {
    return imageGantt;
  }

  public Image getImageHelpWeb() {
    return imageHelpWeb;
  }

  public Image getHadoop() {
    return imageHadoop;
  }

  public void setImageDropHere( Image imageDropHere ) {
    this.imageDropHere = imageDropHere;
  }

  public Image getImageDropHere() {
    return imageDropHere;
  }

  public void setImageAddAll( Image imageAddAll ) {
    this.imageAddAll = imageAddAll;
  }

  public Image getImageAddAll() {
    return imageAddAll;
  }

  public void setImageAddSingle( Image imageAddSingle ) {
    this.imageAddSingle = imageAddSingle;
  }

  public Image getImageAddSingle() {
    return imageAddSingle;
  }

  public void setImageRemoveAll( Image imageRemoveAll ) {
    this.imageRemoveAll = imageRemoveAll;
  }

  public Image getImageRemoveAll() {
    return imageRemoveAll;
  }

  public void setImageRemoveSingle( Image imageRemoveSingle ) {
    this.imageRemoveSingle = imageRemoveSingle;
  }

  public Image getImageRemoveSingle() {
    return imageRemoveSingle;
  }

}
