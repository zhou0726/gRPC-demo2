package com.grpc.grpcdemo.server;

import com.grpc.grpcdemo.grpc.StreamServiceGrpc;
import example.StreamServiceOuterClass;
import io.grpc.stub.StreamObserver;

import java.util.LinkedList;
import java.util.List;

public class StreamService extends StreamServiceGrpc.StreamServiceImplBase {
    @Override
    public StreamObserver<StreamServiceOuterClass.StreamRequest> bidirectionalStreaming(StreamObserver<StreamServiceOuterClass.StreamResponse> responseObserver) {
        return new StreamObserver<StreamServiceOuterClass.StreamRequest>() {
            final List<StreamServiceOuterClass.StreamRequest> requestList = new LinkedList<>();
            @Override
            public void onNext(StreamServiceOuterClass.StreamRequest streamRequest) {
                //接收Client端发送的消息
                requestList.add(streamRequest);
                System.out.println(streamRequest.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                //Server端发送请求
                requestList.forEach(studentInfo -> {
                    StreamServiceOuterClass.StreamResponse responseMeta = StreamServiceOuterClass.StreamResponse.newBuilder().setMessage("Hello，I‘m Server,回答：" + studentInfo.getMessage()).build();
                    System.out.println("Response:" + responseMeta.getMessage());
                    responseObserver.onNext(responseMeta);
                });

                responseObserver.onCompleted();
            }
        };
    }
}

