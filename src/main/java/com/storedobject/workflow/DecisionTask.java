package com.storedobject.workflow;

import java.util.function.Predicate;

/**
 * Decision task. The predicate task ({@link #predicate}) is executed and if the result is <code>true</code>,
 * {@link #yesTask} is executed. Otherwise, {@link #noTask} is executed.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
public final class DecisionTask<T> implements Task<T> {

    private final Predicate<Context<T>> predicate;
    private final Task<T> yesTask, noTask;

    /**
     * Constructor.
     *
     * @param predicate The predicate task to execute to decide on further action.
     * @param yesTask The task to be executed if the predicate task is successfully executed.
     * @param noTask The task to be executed if the predicate task fails to execute.
     */
    public DecisionTask(Predicate<Context<T>> predicate, Task<T> yesTask, Task<T> noTask) {
        this.predicate = predicate;
        this.yesTask = yesTask;
        this.noTask = noTask;
    }

    /**
     * The predicate task ({@link #predicate}) is executed and if the result is <code>true</code>,
     * {@link #yesTask} is executed. Otherwise, {@link #noTask} is executed. So, the result returned will be
     * either result of {@link #yesTask} or the result of {@link #noTask}.
     *
     * @param context Workflow context.
     * @return True/false
     */
    @Override
    public boolean execute(Context<T> context) {
        try {
            boolean r = predicate.test(context);
            context.predecessor = this;
            return r ? yesTask.execute(context) : noTask.execute(context);
        } catch(Throwable ignored) {
        }
        return false;
    }
}
