package edu.yagodka.ch312;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WatchActor extends UntypedActor{

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    final ActorRef child = this.getContext().actorOf(Props.empty(), "child");
    {
        this.getContext().watch(child);
    }
    ActorRef lastSender = getContext().system().deadLetters();

    @Override
    public void onReceive(Object message) throws Throwable {

        if (message.equals("kill")) {
            getContext().stop(child);
            lastSender = getSender();
            log.info("killing...");

        } else if (message instanceof Terminated) {
            final Terminated t = (Terminated) message;
            if (t.getActor() == child) {
                lastSender.tell("finished", getSelf());
                log.info("finishing...");
            }
        } else {
            unhandled(message);
        }
    }
}
