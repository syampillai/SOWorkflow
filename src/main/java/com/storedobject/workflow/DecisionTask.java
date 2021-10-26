package com.storedobject.workflow;

/**
 * Decision task. The predicate task ({@link #predicate}) is executed and if the result is <code>true</code>,
 * {@link #yesTask} is executed. Otherwise, {@link #noTask} is executed.
 *
 * @author Syam
 */
public class DecisionTask implements Task {

    private final Task predicate, yesTask, noTask;

    /**
     * Constructor.
     *
     * @param predicate The predicate task to execute to decide on further action.
     * @param yesTask The task to be executed if the predicate task is successfully executed.
     * @param noTask The task to be executed if the predicate task fails to execute.
     */
    public DecisionTask(Task predicate, Task yesTask, Task noTask) {
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
    public boolean execute(Context context) {
        boolean r = predicate.execute(context);
        context.predecessor = predicate;
        return r ? yesTask.execute(context) : noTask.execute(context);
    }
}
