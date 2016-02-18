package org.piotryuxuan.customignoreannotation;

import org.piotryuxuan.customignoreannotation.CustomIgnore.CustomWrap;

import java.lang.annotation.Annotation;

public final class AnnotationHandler {

    private AnnotationHandler() {
    }

    public static CustomIgnore newCustomIgnore(CustomWrap... values) {
        return new CustomIgnore() {
            @Override
            public boolean equals(Object obj) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String toString() {
                return CustomIgnore.class.toString();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return CustomIgnore.class;
            }

            @Override
            public CustomWrap[] value() {
                return values;
            }
        };
    }
}
