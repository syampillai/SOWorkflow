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
     * @param task Task representing a workflow.
     * @return True if the workflow is fully executed, otherwise, false.
     */
    public static boolean execute(Task task) {
        return execute(task, null);
    }

    /**
     * Execute a workflow. If any sort of exception is thrown while executing the workflow, <code>false</code>
     * will be returned as the result and the exception is silently ignored. It is up to the task coder to handle
     * exceptions withing the {@link Task#execute(Context)} method of the task.
     *
     * @param task Task representing a workflow.
     * @param parameters Parameter object to be made available in the {@link Context} instance passed to the
     *                   workflow task while executing it.
     * @return True if the workflow is fully executed, otherwise, false.
     */
    public static boolean execute(Task task, Object parameters) {
        try {
            return task.execute(new Context(parameters));
        } catch(Throwable e) {
            return false;
        }
    }
}
