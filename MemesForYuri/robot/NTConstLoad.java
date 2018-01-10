package org.usfirst.frc.team4590.utils.ntcore.robot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NTConstLoad {
	String path() default NetworkTablesSyncer.DEFAULT_NAME;
	
}
