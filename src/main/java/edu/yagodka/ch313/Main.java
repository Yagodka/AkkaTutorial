package edu.yagodka.ch313;

import akka.actor.*;
import scala.Option;

public class Main {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef refAnother = system.actorOf(Props.create(Another.class), "another");
        ActorRef refFollower = system.actorOf(Props.create(Follower.class, refAnother));

        ActorIdentity identity = new ActorIdentity("1", Option.apply(refAnother));
        refFollower.tell(identity, refAnother);

        refFollower.tell(new Terminated(refFollower, true, true), refAnother);
        refAnother.tell(new Terminated(ActorRef.noSender(), true, true), ActorRef.noSender());
    }
}
