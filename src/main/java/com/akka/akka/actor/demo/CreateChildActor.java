package com.akka.akka.actor.demo;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;

/**
 * 创建子Actor
 * @author ytgaom
 * @date 2022/10/10 9:36
 */
public class CreateChildActor extends AbstractActor {

    public static Props props() {
        return Props.create(CreateChildActor.class, CreateChildActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("createChild", p -> {
            ActorRef childActor = getContext().actorOf(Props.empty(), "child-actor");
            System.out.println(StrUtil.format("childActor: 收到消息P: {}, 创建子Actor: {}, senderActor: {}, selfActor: {}", p, childActor, sender(), self()));
        }).build();
    }

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("createActor");
        ActorRef parentActor1 = actorSystem.actorOf(CreateChildActor.props(), "parent-actor1");
        System.out.println("parentActor: 创建父Actor1: " + parentActor1);
        ActorRef parentActor2 = actorSystem.actorOf(CreateChildActor.props(), "parent-actor2");
        System.out.println("parentActor: 创建父Actor2: " + parentActor2);
        parentActor1.tell("hello", parentActor2);
        parentActor1.tell("hello", ActorRef.noSender());
        System.out.println("after hello!");
        parentActor1.tell("createChild", parentActor2);
        parentActor2.tell("createChild", ActorRef.noSender());
        System.out.println(">>> Press ENTER to exit <<<");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            actorSystem.terminate();
        }
    }

}
