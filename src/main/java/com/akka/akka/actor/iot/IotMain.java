package com.akka.akka.actor.iot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * IOT启动类
 * @author ytgaom
 * @date 2022/10/10 14:55
 */
public class IotMain {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("iot-system");
        ActorRef parentActor = system.actorOf(IotSupervisor.props(), "iot-supervisor");
    }

}
