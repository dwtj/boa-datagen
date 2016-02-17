package jlssrc.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

class AnnotatedTypeParameter<@AnnotatedTypeParameter$Annotation T> { }

@Target(ElementType.TYPE_PARAMETER)
@interface AnnotatedTypeParameter$Annotation {}
