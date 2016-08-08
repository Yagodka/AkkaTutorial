package edu.yagodka.ch244;

import akka.actor.*;
import akka.pattern.Backoff;
import akka.pattern.BackoffSupervisor;
import akka.util.Timeout;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create();

        final Props childProps = Props.create(EchoActor.class);

        final Props supervisorOnStop = BackoffSupervisor.props(
                Backoff.onStop(
                        childProps,
                        "myEcho",
                        Duration.create(3, TimeUnit.SECONDS),
                        Duration.create(30, TimeUnit.SECONDS),
                        0.2)); // adds 20% "noise" to vary the intervals slightly

        final Props supervisorOnFailure = BackoffSupervisor.props(
                Backoff.onFailure(
                        childProps,
                        "myEcho",
                        Duration.create(3, TimeUnit.SECONDS),
                        Duration.create(30, TimeUnit.SECONDS),
                        0.3));

        system.actorOf(supervisorOnStop, "echoSupervisor");

/*      ActorSelection selection = system.actorSelection("/*");
        System.out.println("| selection:    " + selection);
        System.out.println("| anchor:       " + selection.anchor());
        System.out.println("| anchorPath:   " + selection.anchorPath());
        System.out.println("| pathString:   " + selection.pathString());
*/
    }
}

