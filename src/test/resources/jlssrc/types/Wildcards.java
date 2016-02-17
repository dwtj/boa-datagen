package jlssrc.types;

class Wildcards<T> {
    List<?> unboundedWildcard;
    List<? extends T> upperBoundedWildcard;
    List<? super T> lowerBoundedWildcard;
}