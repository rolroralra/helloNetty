package com.example;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferTest {

    public static void main(String[] args) {

        ByteBuffer header = ByteBuffer.allocate(100);
        header.put("header".getBytes(StandardCharsets.UTF_8));
        header.flip();

        ByteBuffer body = ByteBuffer.allocate(100);
        body.put("body".getBytes(StandardCharsets.UTF_8));
        body.flip();

        ByteBuffer message2 = ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();

        System.out.println(new String(message2.array(), message2.position(), message2.limit()));


        ByteBuffer buffer = ByteBuffer.allocate(100);
        System.out.printf("ByteBuffer.allocate(%d)%n", buffer.capacity());
        print(buffer);

        buffer.put("1234".getBytes(StandardCharsets.UTF_8));
        print(buffer);

        buffer.put("5678".getBytes(StandardCharsets.UTF_8));
        print(buffer);

        buffer.put("9012".getBytes(StandardCharsets.UTF_8));
        print(buffer);

        buffer.put("3456".getBytes(StandardCharsets.UTF_8));
        print(buffer);

        System.out.println(buffer.remaining());

        buffer.flip();
        print(buffer);

        buffer.compact();
        print(buffer);

        buffer.put("7890".getBytes(StandardCharsets.UTF_8));
        print(buffer);

        buffer.flip();
        print(buffer);

        String msg = new String(buffer.array(), buffer.position(), buffer.limit());
        System.out.println(msg);

        System.out.println("\n다시 쓰기");

        buffer.clear();
        print(buffer);

        buffer.put("3456".getBytes(StandardCharsets.UTF_8));
        print(buffer);

        System.out.println("\n읽기 모드");
        buffer.flip();
        print(buffer);

        String message3 = new String(buffer.array(), buffer.position(), buffer.limit());
        System.out.println("Result : " + message3);
    }

    public static void print(ByteBuffer buffer) {
        System.out.printf("capacity: %d, limit: %d, position: %d%n", buffer.capacity(), buffer.limit(), buffer.position());
    }
}
