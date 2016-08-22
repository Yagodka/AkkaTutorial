package edu.yagodka.ch83;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static class Author {
        public final String handle;

        public Author(String handle) {
            this.handle = handle;
        }
    }

    public static class Hashtag {
        public final String name;

        public Hashtag(String name) {
            this.name = name;
        }
    }

    public static class Tweet {
        public final Author author;
        public final long timestamp;
        public final String body;

        public Tweet(Author author, long timestamp, String body) {
            this.author = author;
            this.timestamp = timestamp;
            this.body = body;
        }

        public Set<Hashtag> hashtags() {
            return Arrays.asList(body.split(" ")).stream()
                    .filter(a -> a.startsWith("#"))
                    .map(a -> new Hashtag(a))
                    .collect(Collectors.toSet());
        }
    }

    public static final Hashtag AKKA = new Hashtag("#akka");

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("reactive-tweets");
        final Materializer mat = ActorMaterializer.create(system);


    }

}
