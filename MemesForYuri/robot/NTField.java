package org.usfirst.frc.team4590.utils.ntcore.robot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.usfirst.frc.team4590.utils.ntcore.common.NTFieldType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NTField {
	String name() default NetworkTablesSyncer.DEFAULT_NAME; 
	NTFieldType type() default NTFieldType.AUTO;
}
