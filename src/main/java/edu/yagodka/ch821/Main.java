package edu.yagodka.ch821;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import akka.util.ByteString;

import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.concurrent.CompletionStage;

class StreamsDemo {

    public StreamsDemo() {

        final ActorSystem system = ActorSystem.create("QuickStart");
        final Materializer materializer = ActorMaterializer.create(system);

        final Source<Integer, NotUsed> source = Source.range(1, 100);
        source.runForeach(System.out::println, materializer);

        final Source<BigInteger, NotUsed> factorials =
                source.scan(BigInteger.ONE,
                        ((acc, next) -> acc.multiply(BigInteger.valueOf(next))));

        final CompletionStage<IOResult> result =
                factorials.map(BigInteger::toString).runWith(lineSink("factorials.txt"), materializer);
    }

    public Sink<String, CompletionStage<IOResult>> lineSink(String filename) {
        return Flow.of(String.class)
                .map(s -> ByteString.fromString(s.toString() + "\n"))
                .toMat(FileIO.toPath(Paths.get(filename)), Keep.right());
    }
}


public class Main {
    public static void main(String[] args) {
        new StreamsDemo();
    }
}
