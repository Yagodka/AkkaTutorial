package edu.yagodka.ch821;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Source;

import java.math.BigInteger;
import java.util.Random;

public class GetData {

    public static void main(String[] args) {

        Random random = new Random();

        final ActorSystem system = ActorSystem.create();
        final Materializer materializer = ActorMaterializer.create(system);

        final Source<Integer, NotUsed> source = Source.range(1, 1000);
        source
                .map(param -> random.nextInt(49)+1)
                .runForeach(System.out::println, materializer);

        // rand.nextInt((max - min) + 1) + min
//        long l = new Random()
//                .ints()
//                .distinct()
//                .peek(e -> System.out.println("Random value: " + e))
//                .limit(100)
//                .count()
//        ;
//        System.out.println(l);

    }
}
