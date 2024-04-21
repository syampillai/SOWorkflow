package com.storedobject.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a bunch of tasks and one of them is executed based on an index number generated by an integer function.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
public final class BranchingTask<T> implements Task<T> {

    private final List<Task<T>> list = new ArrayList<>();
    private final Function<Context<T>, Integer> indexFunction;
    private final boolean indexOutOfBoundResult;

    /**
     * Constructor. (If the index is out of range, it is not considered as the failure).
     *
     * @param indexFunction Index generation function to determine the branch.
     * @param first First task (corresponding to index = 0).
     * @param second Second task (corresponding to index = 1).
     * @param more More tasks for index values > 1.
     */
    @SafeVarargs
    public BranchingTask(Function<Context<T>, Integer> indexFunction, Task<T> first, Task<T> second, Task<T>... more) {
        this(indexFunction, true, first, second, more);
    }

    /**
     * Constructor.
     *
     * @param indexFunction Index generation function to determine the branch.
     * @param indexOutOfBoundResult Result of execution if the index is out of range.
     * @param first First task (corresponding to index = 0).
     * @param second Second task (corresponding to index = 1).
     * @param more More tasks for index values > 1.
     */
    @SafeVarargs
    public BranchingTask(Function<Context<T>, Integer> indexFunction, boolean indexOutOfBoundResult,
                         Task<T> first, Task<T> second, Task<T>... more) {
        this.indexOutOfBoundResult = indexOutOfBoundResult;
        this.indexFunction = indexFunction;
        list.add(first);
        list.add(second);
        if(more != null) {
            Collections.addAll(list, more);
        }
    }

    /**
     * Execute the task based on the index number generated by the {@link #indexFunction}.
     *
     * @param context Workflow context.
     * @return True/false.
     */
    @Override
    public boolean execute(Context<T> context) {
        try {
            int index = indexFunction.apply(context);
            if(index >= 0 && index < list.size()) {
                return list.get(index).execute(context);
            }
            return indexOutOfBoundResult;
        } catch(Throwable error) {
            return false;
        } finally {
            context.predecessor = this;
        }
    }
}
