package io.github.suzunshou.buffers;

import java.lang.reflect.Array;

/*
 * @author suzunshou 2019-12-19 20:29:17
 * A buffer pool which keeps a free list of direct buffers of a specified default
 * size in a simple fixed size stack.
 * If the stack is full the buffer is de-referenced and available to be
 * freed by normal garbage collection.
 */
public class BufferPool<T> {

    private int buffersInPool = 0;
    private int maxPoolEntries;
    private int bufferSize;
    private Buffer<T>[] pool;

    public BufferPool(int maxPoolEntries, int bufferSize) {
        if (maxPoolEntries <= 0) {
            throw new IllegalArgumentException("maxPoolEntries must > 0!");
        }
        this.maxPoolEntries = maxPoolEntries;
        this.bufferSize = bufferSize;
        pool = (Buffer<T>[]) Array.newInstance(Buffer.class, maxPoolEntries);
    }

    public Buffer<T> acquire() {
        return takeBufferFromPool();
    }

    private Buffer<T> takeBufferFromPool() {
        Buffer<T> result = null;
        synchronized (this) {
            if (buffersInPool > 0) {
                buffersInPool -= 1;
                result = pool[buffersInPool];
            }
        }
        if (result == null) {
            result = new Buffer<>(bufferSize);
        }
        return result;
    }

    public void release(Buffer<T> buffer) {
        offerBufferToPool(buffer);
    }

    private void offerBufferToPool(Buffer<T> buffer) {
        synchronized (this) {
            if (buffersInPool < maxPoolEntries) {
                pool[buffersInPool] = buffer;
                buffersInPool += 1;
            }
        }
    }
}
