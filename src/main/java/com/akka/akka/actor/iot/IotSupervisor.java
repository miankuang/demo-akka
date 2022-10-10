package com.akka.akka.actor.iot;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 顶级IOT监控actor
 * @author ytgaom
 * @date 2022/10/10 14:51
 */
public class IotSupervisor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public void preStart() throws Exception {
        log.info("【IotSupervisor】IOT actor:[{}]创建完成，可以接收消息", self());
    }

    @Override
    public void postStop() throws Exception {
        log.info("【IotSupervisor】IOT actor:[{}]开始停止", self());
    }

    public static Props props() {
        return Props.create(IotSupervisor.class, IotSupervisor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
