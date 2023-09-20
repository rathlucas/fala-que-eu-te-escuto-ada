package dev.lucas.preview.util.stack;

public interface Stack<T> {

    T push(T element);

    T pop();

    T top();

    boolean isEmpty();

    int size();
}
