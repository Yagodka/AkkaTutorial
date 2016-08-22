package edu.yagodka;

import akka.dispatch.*;
import akka.pattern.Patterns;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.util.Try;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        IntStream
//            .range(1, 11)
//            .map(t -> t * t)
//            .forEach(System.out::println);

        System.out.println("Start!");

        final ExecutionContext ex = ExecutionContexts.global();
        Futures.future(
                () -> {
                    String s = "From Future!";
                    System.out.println("Before sleep " + s);
                    Thread.sleep(10000);
                    System.out.println("After sleep " + s);
                    return s;
                }, ex)
                .andThen(
                    new OnComplete<String>() {
                        public void onComplete(Throwable failure, String result) {
                            System.out.println("OnComplete " + result);
                        }
                    }, ex);

        Thread.sleep(20000);
        System.out.println("End!");
    }

}
