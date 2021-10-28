package com.storedobject.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Two or more tasks to be executed in parallel. All the tasks will be executed in parallel irrespective of any
 * task failures.
 *
 * @param <T> Type of item used in the workflow.
 * @author Syam
 */
public final class ParallelTask<T> implements Task<T> {

    private final List<Task<T>> list = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param first First task to be executed.
     * @param second Second task to be executed.
     * @param more More tasks if any to be executed.
     */
    @SafeVarargs
    public ParallelTask(Task<T> first, Task<T> second, Task<T>... more) {
        list.add(first);
        list.add(second);
        if(more != null) {
            Collections.addAll(list, more);
        }
    }

    /**
     * Execute all the tasks in parallel. It returns <code>true</code> if all the tasks are successfully executed,
     * otherwise, <code>false</code> is returned.
     *
     * @param context Workflow context
     * @return True/false
     */
    @Override
    public boolean execute(Context<T> context) {
        List<Future<Boolean>> results = new ArrayList<>();
        try {
            list.forEach(w -> results.add(context.execute(w)));
        } catch(Throwable e) {
            context.predecessor = this;
            return false;
        }
        boolean result = true;
        for(Future<Boolean> r: results) {
            try {
                result = result && r.get();
            } catch(Throwable e) {
                result = false;
            }
        }
        context.predecessor = this;
        return result;
    }
}
