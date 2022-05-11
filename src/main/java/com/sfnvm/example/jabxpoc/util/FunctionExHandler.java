package com.sfnvm.example.jabxpoc.util;

import java.util.function.Function;

@FunctionalInterface
public interface FunctionExHandler<T, R, E extends Exception> {
    R apply(T t) throws E;

    static <T, R> Function<T, R> handling(FunctionExHandler<T, R, Exception> handler) {
        return obj -> {
            try {
                return handler.apply(obj);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        };
    }
}
