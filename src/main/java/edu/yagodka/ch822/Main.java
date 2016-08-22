package edu.yagodka.ch822;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import scala.concurrent.duration.Duration;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

class StreamsDemo {

    public StreamsDemo() {

        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);

        final Source<BigInteger, NotUsed> factorials =
                Source.range(1, 20)
                        .scan(BigInteger.ONE,
                        ((acc, next) -> acc.multiply(BigInteger.valueOf(next))));

        final CompletionStage<Done> done =
                factorials
                        .zipWith(Source.range(0, 99), (num, idx) -> String.format("%d! = %s", idx, num))
                        .throttle(1, Duration.create(10, TimeUnit.MILLISECONDS), 1, ThrottleMode.shaping())
                        .runForeach(s -> System.out.println(s), materializer);
    }
}


public class Main {
    public static void main(String[] args) {
        new StreamsDemo();
    }
}
