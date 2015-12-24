package net.simplelib.annotation.field;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Inherited
public @interface Watching
{}
