package com.Shubham.Url_Shortener.Util;

import java.nio.ByteBuffer;

public class Util {

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static Long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        System.out.println(buffer.getLong());
        return (Long) buffer.getLong();
    }
}
