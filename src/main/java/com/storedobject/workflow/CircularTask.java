package com.storedobject.workflow;

/**
 * Two or more tasks to be executed sequentially and repeated from the first task again forever until a task fails.
 * If any one of the tasks fails to execute, the rest of the tasks are ignored. The expected design is that one of
 * the tasks eventually fails and a return value from the circular task is always a <code>false</code>.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
public final class CircularTask<T> implements Task<T> {

    private final SerialTask<T> serialTask;

    /**
     * Constructor.
     *
     * @param first First task to be executed.
     * @param second Second task to be executed.
     * @param more More tasks if any to be executed.
     */
    @SafeVarargs
    public CircularTask(Task<T> first, Task<T> second, Task<T>... more) {
        serialTask = new SerialTask<>(first, second, more);
    }

    /**
     * Execute the tasks sequentially. Operation is repeated if all the tasks are executed. If any one of
     * the tasks fails to execute, the rest of the tasks are ignored and <code>false</code> is returned.
     *
     * @param context Workflow context
     * @return False
     */
    @Override
    public boolean execute(Context<T> context) {
        //noinspection StatementWithEmptyBody
        while(serialTask.execute(context));
        context.predecessor = this;
        return false;
    }
}
