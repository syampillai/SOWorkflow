package com.storedobject.workflow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Workflow context.
 *
 * @author Syam
 */
public class Context {

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    Task predecessor;
    private Object parameters;

    /**
     * Constructor (to be used internally).
     *
     * @param parameters Parameter object to be made available when this instance is passed to the
     *                   workflow task while executing it.
     */
    Context(Object parameters) {
        this.parameters = parameters;
    }

    /**
     * Utility method to execute a task in the background.
     *
     * @param task Task to be executed.
     * @return Result of the task execution as a {@link Future}.
     */
    Future<Boolean> execute(Task task) {
        return executor.submit(() -> task.execute(this));
    }

    /**
     * Get the predecessor task in the workflow chain that invoked the current task.
     * @return The predecessor task. For the fist task in the workflow, it will be <code>null</code>.
     */
    public Task getPredecessor() {
        return predecessor;
    }

    /**
     * Get the parameters set.
     *
     * @return Parameters object.
     */
    public Object getParameters() {
        return parameters;
    }

    /**
     * Set parameters. Any sort of parameter structure can be defined and set either by individual tasks or while
     * starting the execution of the workflow.
     *
     * @param parameters Parameters to be set.
     */
    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }
}
