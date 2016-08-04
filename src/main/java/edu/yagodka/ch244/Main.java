package edu.yagodka.ch244;

import akka.actor.*;
import akka.pattern.Backoff;
import akka.pattern.BackoffSupervisor;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create();

        final Props childProps = Props.create(EchoActor.class);

        final Props supervisorProps = BackoffSupervisor.props(
                Backoff.onStop(
                        childProps,
                        "myEcho",
                        Duration.create(3, TimeUnit.SECONDS),
                        Duration.create(30, TimeUnit.SECONDS),
                        0.2)); // adds 20% "noise" to vary the intervals slightly

        system.actorOf(supervisorProps, "echoSupervisor");
    }
}
