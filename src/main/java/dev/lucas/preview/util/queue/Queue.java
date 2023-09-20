package dev.lucas.preview.util.queue;

public interface Queue<T> {

    T enqueue(T element);

    T dequeue();

    T front();

    T rear();

    boolean isEmpty();

    boolean isFull();
}
