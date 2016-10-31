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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RepositoryObjectSessionTimeoutHandler implements InvocationHandler {

  private final Object repositoryObject;

  private final SessionTimeoutHandler sessionTimeoutHandler;

  public RepositoryObjectSessionTimeoutHandler( Object repositoryObject, SessionTimeoutHandler sessionTimeoutHandler ) {
    this.repositoryObject = repositoryObject;
    this.sessionTimeoutHandler = sessionTimeoutHandler;
  }

  @Override
  public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
    try {
      return method.invoke( repositoryObject, args );
    } catch ( InvocationTargetException ex ) {
      return sessionTimeoutHandler.handle( repositoryObject, ex.getCause(), method, args );
    }
  }

}
