package com.storedobject.test;

import com.storedobject.workflow.*;

import java.util.function.Consumer;

public class Test {

    public static void main(String[] args) {
        Task<String> t1 = StringTask.get(System.err::println),
                t2 = StringTask.get(s -> System.err.println(s.toLowerCase())),
        t3 = StringTask.get(s -> System.err.println(s.toUpperCase())),
        t4 = c -> { System.err.println("--------"); return true; };
        Engine.execute(new RepeatTask<>(new SerialTask<>(t1, t2, t3, t4), 3), "Hello world!");
    }

    private static class StringTask implements Task<String> {

        private final Consumer<String> action;

        private StringTask(Consumer<String> action) {
            this.action = action;
        }

        @Override
        public boolean execute(Context<String> context) {
            action.accept(context.getItem());
            return true;
        }

        private static StringTask get(Consumer<String> action) {
            return new StringTask(action);
        }
    }
}
