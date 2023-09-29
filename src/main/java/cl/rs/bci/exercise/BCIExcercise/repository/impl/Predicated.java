package cl.rs.bci.exercise.BCIExcercise.repository.impl;

@FunctionalInterface
public interface Predicated<T, R> {

    R invoke(T t);
}
