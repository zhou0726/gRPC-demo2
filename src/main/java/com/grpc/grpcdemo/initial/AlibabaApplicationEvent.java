package com.grpc.grpcdemo.initial;

import org.springframework.context.ApplicationEvent;

public class AlibabaApplicationEvent extends ApplicationEvent {

    public AlibabaApplicationEvent(Object source) {
        super(source);
    }
}