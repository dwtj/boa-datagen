package jlssrc.classes.header.superinterfaces;

class TakesTypeArgumentFromClass<T> implements Comparable<T> {
    public int compareTo(T other) {
        return 0;
    }
}