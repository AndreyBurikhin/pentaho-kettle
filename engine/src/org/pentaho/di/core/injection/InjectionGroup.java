package org.pentaho.di.core.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.TYPE } )
public @interface InjectionGroup {
  String name();

  String description();
}
