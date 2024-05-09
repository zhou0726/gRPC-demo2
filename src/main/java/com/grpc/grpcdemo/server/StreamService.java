package com.grpc.grpcdemo.server;

import com.grpc.grpcdemo.grpc.StreamServiceGrpc;
import com.grpc.grpcdemo.service.AlibabaService;
import example.StreamServiceOuterClass;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class StreamService extends StreamServiceGrpc.StreamServiceImplBase {

    @Autowired
    private AlibabaService alibabaService;
    @Override
    public StreamObserver<StreamServiceOuterClass.StreamRequest> bidirectionalStreaming(StreamObserver<StreamServiceOuterClass.StreamResponse> responseObserver) {
        return new StreamObserver<StreamServiceOuterClass.StreamRequest>() {
            final List<StreamServiceOuterClass.StreamRequest> requestList = new LinkedList<>();
            @Override
            public void onNext(StreamServiceOuterClass.StreamRequest streamRequest) {
                //接收Client端发送的消息
                requestList.add(streamRequest);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                //Server端发送请求
                requestList.forEach(studentInfo -> {

                    //访问阿里验证,获取到阿里验证的结果，
//                    String rpcCheck = alibabaService.rpcCheck();
                    String rpcCheck = "阿里验证成功";

                    StreamServiceOuterClass.StreamResponse responseMeta = StreamServiceOuterClass.StreamResponse.newBuilder().setMessage(rpcCheck).build();
                    responseObserver.onNext(responseMeta);
                });

                responseObserver.onCompleted();
            }
        };
    }
}

