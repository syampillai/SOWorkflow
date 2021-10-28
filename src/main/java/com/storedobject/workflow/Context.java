package com.storedobject.workflow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Workflow context.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
public final class Context<T> {

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private final T item;
    /**
     * The predecessor task.
     */
    Task<T> predecessor;

    /**
     * Constructor (to be used internally).
     *
     * @param item Item to be made available when this instance is passed to the
     *                   workflow task while executing it.
     */
    Context(T item) {
        this.item = item;
    }

    /**
     * Utility method to execute a task in the background.
     *
     * @param task Task to be executed.
     * @return Result of the task execution as a {@link Future}.
     */
    Future<Boolean> execute(Task<T> task) {
        return executor.submit(() -> task.execute(this));
    }

    /**
     * Get the predecessor task in the workflow chain that invoked the current task.
     * @return The predecessor task. For the fist task in the workflow, it will be <code>null</code>.
     */
    public Task<T> getPredecessor() {
        return predecessor;
    }

    /**
     * Get the item of the workflow.
     *
     * @return Item of the workflow.
     */
    public T getItem() {
        return item;
    }
}
