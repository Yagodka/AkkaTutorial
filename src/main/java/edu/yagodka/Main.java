package edu.yagodka;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        IntStream
            .range(1, 11)
            .map(t -> t * t)
            .forEach(System.out::println);

    }
}
