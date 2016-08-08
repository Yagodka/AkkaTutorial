package edu.yagodka.ch244;

import akka.actor.*;
import scala.Function1;

class EchoActor extends UntypedActor{
    @Override
    public void onReceive(Object message) throws Throwable {
        System.out.println("Echo Actor...");
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println("Prestart " + self());
        System.out.println("Parrent " +  context().parent());

        throw new Exception("");
    }
}
