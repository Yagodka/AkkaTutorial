package edu.yagodka.ch244;

import akka.actor.UntypedActor;

class EchoActor extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println("Echo Actor...");
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println("Prestart" + self());
        throw new Exception();
    }
}
