package org.pentaho.di.ui.repository.dialog;

import org.eclipse.swt.SWT;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.vfs.ui.CustomVfsUiPanel;
import org.pentaho.vfs.ui.VfsFileChooserDialog;

public class RepositoryVfsFileChooserDialog extends CustomVfsUiPanel {

  private LogChannelInterface log;

  public RepositoryVfsFileChooserDialog( VfsFileChooserDialog vfsFileChooserDialog ) {
    super( "repo", "Repository", vfsFileChooserDialog, SWT.None );
    log = new LogChannel( this.getClass().getSimpleName() );
  }

  @Override
  public void activate() {
    // TODO put #activation code here...
    log.logBasic( "#activate" );
  }

}
