package org.piotryuxuan.customignoreannotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.Before;
import static org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap.After;

@Retention(RetentionPolicy.RUNTIME)
public @interface CustomIgnore {
    public static enum CustomWrap {Before, After}

    CustomWrap[] value() default {Before, After};
}