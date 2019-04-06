package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface Functor<T> {
    <R> Functor<R> map(Function<? super T, ? extends R> mapper);
}
