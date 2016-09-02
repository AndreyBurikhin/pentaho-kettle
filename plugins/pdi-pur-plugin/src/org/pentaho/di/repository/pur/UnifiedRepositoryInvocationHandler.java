/*!
 * Copyright 2010 - 2016 Pentaho Corporation.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.pentaho.di.repository.pur;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentaho.di.core.extension.ExtensionPointHandler;
import org.pentaho.di.core.extension.KettleExtensionPoint;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.repository.KettleRepositoryLostException;

class UnifiedRepositoryInvocationHandler<T> implements InvocationHandler {

  private LogChannelInterface log;

  private T rep;
  
  private AtomicBoolean needToLogin = new AtomicBoolean(false);

  // private Repository owner;

  UnifiedRepositoryInvocationHandler( T rep ) {
    this.rep = rep;
    log = new LogChannel( this );
  }

  @Override
  public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
    try {
      return method.invoke( rep, args );
    } catch ( InvocationTargetException ex ) {
      if ( lookupConnectException( ex ) && !callFromHandler()) {
        needToLogin.set( true );
        synchronized ( this ) {
          if ( needToLogin.get() ) {
            KettleRepositoryLostException klre = new KettleRepositoryLostException( ex.getCause() );
            ExtensionPointHandler.callExtensionPoint( log, KettleExtensionPoint.RepositoryOperationException.id, klre );
            needToLogin.set( false );
          }
        }
        return method.invoke( rep, args );
        // throw new KettleRepositoryLostException( ex.getCause() );
      }

      throw ex.getCause();
    }
  }

  private boolean callFromHandler() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    if ( stackTrace.length < 4 ) {
      return false;
    }
    for ( int i = 4; i < stackTrace.length; i++ ) {
      if ( stackTrace[i].getClassName().equals( UnifiedRepositoryInvocationHandler.class.getCanonicalName() ) ) {
        return true;
      }
    }
    return false;
  }

  private boolean lookupConnectException( Throwable root ) {
    while ( root != null ) {
      if ( root instanceof ConnectException ) {
        return true;
      } else {
        root = root.getCause();
      }
    }

    return false;
  }

  @SuppressWarnings( "unchecked" )
  public static <T> T forObject( T o, Class<T> clazz ) {
    return (T) Proxy.newProxyInstance( o.getClass().getClassLoader(), new Class<?>[] { clazz },
        new UnifiedRepositoryInvocationHandler<T>( o ) );
  }

}
