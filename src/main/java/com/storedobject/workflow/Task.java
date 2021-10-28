package com.storedobject.workflow;

/**
 * Definition of a task. A task is a piece of work that can be executed. Tasks can be combined using other task
 * definitions (such as {@link SerialTask}, {@link ParallelTask}, {@link DecisionTask} etc.) to create complex
 * tasks and when a task is complex, we may call it a workflow.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
@FunctionalInterface
public interface Task<T> {

    /**
     * A dummy task that always succeeds.
     */
    Task<?> SUCCESS = context -> true;

    /**
     * A dummy task that always fails.
     */
    Task<?> FAILURE = context -> false;

    /**
     * Execute the task. If the execution fails (by returning <code>false</code> from this method),
     * the workflow will be terminated. Otherwise, it will continue with
     * the execution of the next task configured. If a run-time exception is raised while executing a task, it will
     * be considered as a failure and hence, all exceptions must be properly handled by the task execution logic
     * itself.
     *
     * @param context Workflow context.
     * @return True if the task is executed successfully, otherwise false.
     */
    boolean execute(Context<T> context);
}
