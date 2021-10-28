package com.storedobject.workflow;

/**
 * The engine that executes a workflow.
 *
 * @author Syam
 */
public final class Engine {

    private Engine() {
    }

    /**
     * Execute a workflow. If any sort of exception is thrown while executing the workflow, <code>false</code>
     * will be returned as the result and the exception is silently ignored. It is up to the task coder to handle
     * exceptions withing the {@link Task#execute(Context)} method of the task.
     *
     * @param <T> Type of item used in the workflow.
     * @param task Task representing a workflow.
     * @param item Item to be made available in the {@link Context} instance passed to the
     *                   workflow task while executing it.
     */
    public static <T> void execute(Task<T> task, T item) {
        try {
            task.execute(new Context<>(item));
        } catch(Throwable ignored) {
        }
    }
}
