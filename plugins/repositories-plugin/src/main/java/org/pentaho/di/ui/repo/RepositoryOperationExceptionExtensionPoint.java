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

package org.pentaho.di.ui.repo;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.extension.ExtensionPoint;
import org.pentaho.di.core.extension.ExtensionPointInterface;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.KettleRepositoryLostException;
import org.pentaho.di.repository.ReconnectableRepository;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.ui.spoon.Spoon;

@ExtensionPoint(
    id = "RepositoryOperationExceptionExtensionPoint",
    extensionPointId = "RepositoryOperationException",
    description = "Handle error on repository operation"
  )
public class RepositoryOperationExceptionExtensionPoint implements ExtensionPointInterface {

  private static Class<?> PKG = RepositoryOperationExceptionExtensionPoint.class;

  private RepositoryConnectController repositoryConnectController;

  public RepositoryOperationExceptionExtensionPoint( RepositoryConnectController repositoryConnectController ) {
    this.repositoryConnectController = repositoryConnectController;
  }
  
  @Override
  public void callExtensionPoint( LogChannelInterface log, Object object ) throws KettleException {
    if ( !( object instanceof KettleRepositoryLostException ) || !repositoryConnectController.isConnected() ) {
      return;
    }
//    RepositoryMeta repositoryMeta = repositoryConnectController.getConnectedRepository();
//    if ( repositoryMeta != null ) {
//      if ( repositoryMeta.getId().equals( "KettleFileRepository" ) ) {
//        repositoryConnectController.connectToRepository( repositoryMeta );
//      } else {
//        String message = BaseMessages.getString( PKG, "Repository.Reconnection.Message" );
//        new RepositoryDialog( getSpoon().getShell(), repositoryConnectController ).openLogin( repositoryMeta, message );
//      }
//    }
    Repository repository = getSpoon().rep;
    if ( !( repository instanceof ReconnectableRepository ) ) {
      return;
    }
    RepositoryMeta repositoryMeta = repositoryConnectController.getConnectedRepository();
    String message = BaseMessages.getString( PKG, "Repository.Reconnection.Message" );
    new RepositoryDialog( getSpoon().getShell(), repositoryConnectController ).openRelogin( repositoryMeta, message );
  }
  
  private Spoon getSpoon() {
    return Spoon.getInstance();
  }

}
