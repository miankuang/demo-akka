package com.akka.akka.actor.demo;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 生命周期Actor
 * @author ytgaom
 * @date 2022/10/10 10:27
 */
public class LifecycleActor extends AbstractActor {

    ActorRef childActor2 = getContext().actorOf(Props.create(ChildActor.class), "childActor2");

    /**
     * actor创建时，调用
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        System.out.println(StrUtil.format("开始接收该Actor:[{}]的第一个消息", self()));
        ActorRef ch = getContext().actorOf(Props.create(ChildActor.class), "childActor");
        System.out.println(StrUtil.format("创建子Actor:[{}]，并且在父Actor销毁后，子Actor也会销毁", ch));
    }

    /**
     * actor stop时或者ActorSystem.terminate()时调用
     * @throws Exception
     */
    @Override
    public void postStop() throws Exception {
        System.out.println(StrUtil.format("结束该Actor:[{}]运行", self()));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("stop", p -> {
            System.out.println(StrUtil.format("当前Actor:[{}]，接收到消息:{}", self(), p));
            //getContext().stop(self());
            childActor2.tell("hello", self());
        }).build();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        ActorSystem system = ActorSystem.create("createLifecycle");
        ActorRef lifecycleActor = system.actorOf(Props.create(LifecycleActor.class), "lifecycleActor");
        lifecycleActor.tell("stop", ActorRef.noSender());
//        System.out.println("休眠3秒");
//        TimeUnit.SECONDS.sleep(3);
        System.in.read();
        system.terminate();
    }

    public static class ChildActor extends AbstractActor {

        @Override
        public void preStart() throws Exception {
            System.out.println("ChildActor preStart");
        }

        @Override
        public void postStop() throws Exception {
            System.out.println("ChildActor postStop");
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(p -> {
                System.out.println("ChildActor 收到一条消息：" + p);
                throw new RuntimeException("消息处理异常");
            }).build();
        }
    }

}
