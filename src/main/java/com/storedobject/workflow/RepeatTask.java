package com.storedobject.workflow;

import java.util.function.Predicate;

/**
 * A task that will be executed repeatedly while a condition is true.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
public final class RepeatTask<T> implements Task<T> {

    private final Task<T> task;
    private final Predicate<Context<T>> predicate;
    private final boolean noRepeatResult;

    /**
     * Constructor. (Default value of {@link #noRepeatResult} is <code>true</code>).
     *
     * @param task Task that will be repeated.
     * @param predicate The predicate to test.
     */
    public RepeatTask(Task<T> task, Predicate<Context<T>> predicate) {
        this(task, predicate, true);
    }

    /**
     * Constructor.
     *
     * @param task Task that will be repeated.
     * @param predicate The predicate to test.
     * @param noRepeatResult This will be the execution result if the predicate is false in the beginning itself (that
     *                       means the task is not even executed once).
     */
    public RepeatTask(Task<T> task, Predicate<Context<T>> predicate, boolean noRepeatResult) {
        this.task = task;
        this.predicate = predicate;
        this.noRepeatResult = noRepeatResult;
    }

    /**
     * Constructor.
     *
     * @param task Task to be executed.
     * @param count Number of times the task to be executed.
     */
    public RepeatTask(Task<T> task, int count) {
        this(task, count, true);
    }

    /**
     * Constructor.
     *
     * @param task Task to be executed.
     * @param count Number of times the task to be executed.
     * @param noCountResult The result of the execution if the task is not at all executed when the count is zero
     *                       already.
     */
    public RepeatTask(Task<T> task, int count, boolean noCountResult) {
        this(task, new CountDown<>(count), noCountResult);
    }

    /**
     * Execute the task. If the predicate is <code>false</code> initially itself, {@link #noRepeatResult} is returned.
     * Otherwise, it will return <code>false</code> if the execution of the task fails and <code>true</code> if the
     * execution succeeds and the predicate is <code>false</code>.
     *
     * @param context Workflow context.
     * @return True/false.
     */
    @Override
    public boolean execute(Context<T> context) {
        boolean ok = noRepeatResult;
        try {
            while(predicate.test(context)) {
                ok = task.execute(context);
                if(!ok) {
                    return false;
                }
            }
        } catch(Throwable error) {
            return false;
        } finally {
            context.predecessor = this;
        }
        return ok;
    }

    private static class CountDown<O> implements Predicate<Context<O>> {

        private int count;

        private CountDown(int count) {
            this.count = count;
        }

        @Override
        public boolean test(Context<O> context) {
            return count-- > 0;
        }
    }
}
