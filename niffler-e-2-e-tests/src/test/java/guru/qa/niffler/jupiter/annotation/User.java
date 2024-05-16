package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.constant.Action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.FIELD, ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
public @interface User {

    Action action();

}