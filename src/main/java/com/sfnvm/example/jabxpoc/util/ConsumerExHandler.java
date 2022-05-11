package com.sfnvm.example.jabxpoc.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface ConsumerExHandler<T, E extends Exception> {
    void accept(T t) throws E;

    static <T> Consumer<T> handling(ConsumerExHandler<T, Exception> handler) {
        return obj -> {
            try {
                handler.accept(obj);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        };
    }
}
