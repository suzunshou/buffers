package io.github.suzunshou.buffers.test;

import io.github.suzunshou.buffers.Buffer;
import io.github.suzunshou.buffers.BufferPool;
import org.junit.Test;

import java.util.List;

public class TestCase {
    @Test
    public void testBuffer() {
        int maxPoolEntries = 1024;
        int bufferSize = 100;
        BufferPool bufferPool = new BufferPool(maxPoolEntries, bufferSize);
        Buffer<String> buffer = bufferPool.acquire();
        for (int i = 0; i < 10000; i++) {
            buffer.accept("s" + i);
        }
        List<String> list = buffer.drain();
        System.out.println(list);
        bufferPool.release(buffer);
    }
}
