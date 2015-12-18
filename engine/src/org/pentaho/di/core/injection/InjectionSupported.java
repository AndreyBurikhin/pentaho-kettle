package org.pentaho.di.core.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Metadata object should be marked by this annotation if it supports metadata injection.
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
public @interface InjectionSupported {
  String localizationPrefix();

  String[] groups();
}
