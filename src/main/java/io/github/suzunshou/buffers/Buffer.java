package io.github.suzunshou.buffers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * @author suzunshou 2019-12-19 20:29:12
 */
public class Buffer<T> implements BufferFilter<T> {

    private int capacity;
    private final List<T> elements = new ArrayList<>();
    private boolean bufferFull;

    public Buffer(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean accept(T element) {
        if (bufferFull) {
            return false;
        }
        elements.add(element);
        if (elements.size() == capacity) {
            bufferFull = true;
        }
        return true;
    }

    public <R> R drain() {
        if (elements.isEmpty()) {
            return (R) Collections.emptyList();
        }
        Object result = new ArrayList<>(elements);
        clear();
        return (R) result;
    }

    public void clear() {
        elements.clear();
        bufferFull = false;
    }
}
