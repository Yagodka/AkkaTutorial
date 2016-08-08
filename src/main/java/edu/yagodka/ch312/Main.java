package edu.yagodka.ch312;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;

public class Main {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("My-System");
        ActorRef ref = system.actorOf(Props.create(WatchActor.class), "WatchActor");
        ref.tell("kill", ActorRef.noSender());
        ref.tell(new Terminated(ref, true, true), ActorRef.noSender());
    }
}
