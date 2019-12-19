package io.github.suzunshou.buffers;

/*
 * @author suzunshou 2019-12-19 20:26:13
 */
public interface BufferFilter<T> {
    
    boolean accept(T element);
}
