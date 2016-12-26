package org.pentaho.di.ui.repository;

import org.eclipse.swt.SWT;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.vfs.ui.CustomVfsUiPanel;
import org.pentaho.vfs.ui.VfsFileChooserDialog;

public class PurVfsFileChooserDialog extends CustomVfsUiPanel {

  private LogChannelInterface log;
  
  public PurVfsFileChooserDialog( VfsFileChooserDialog vfsFileChooserDialog ) {
    super( "repo", "Repository", vfsFileChooserDialog, SWT.None );
    log = new LogChannel( this.getClass().getSimpleName() );
  }
  
  @Override
  public void activate() {
    //TODO put #activation code here...
    log.logBasic( "#activate" );
  }

}
