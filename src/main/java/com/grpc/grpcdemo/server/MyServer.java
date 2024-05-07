package com.grpc.grpcdemo.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class MyServer {
    public static void main(String[] args) throws Exception {
        Server s = ServerBuilder.forPort(50065)
                .addService(new StreamService())
                .build();
        s.start();
        System.out.println("server started......");
        s.awaitTermination();
    }
}

