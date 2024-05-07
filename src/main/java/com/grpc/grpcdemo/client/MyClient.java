package com.grpc.grpcdemo.client;

import com.grpc.grpcdemo.grpc.StreamServiceGrpc;
import example.StreamServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyClient {
    final static CountDownLatch finishLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("localhost:50065").usePlaintext()
                .build();


        StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(managedChannel);
        StreamObserver<StreamServiceOuterClass.StreamResponse> streamObserver = new StreamObserver<StreamServiceOuterClass.StreamResponse>() {
            @Override
            public void onNext(StreamServiceOuterClass.StreamResponse streamResponse) {
                //接收到Server返回的消息
                System.out.println(streamResponse.getMessage() + "-" + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("finished......");
                finishLatch.countDown();
            }
        };


        StreamObserver<StreamServiceOuterClass.StreamRequest> requestObserver = stub.bidirectionalStreaming(streamObserver);
        try {

            //组装发送请求Request
            for (int i = 0; i < 10; ++i) {
                StreamServiceOuterClass.StreamRequest req = StreamServiceOuterClass.StreamRequest.newBuilder().setMessage("请求：" + ( 20 + i ) ).build();
                requestObserver.onNext(req);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        requestObserver.onCompleted();

        finishLatch.await(10, TimeUnit.MINUTES);
    }
}

