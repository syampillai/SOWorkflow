package com.storedobject.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Two or more tasks to be executed sequentially. All the tasks will be executed in parallel.
 *
 * @author Syam
 */
public final class ParallelTask implements Task {

    private final List<Task> list = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param first First task to be executed.
     * @param second Second task to be executed.
     * @param more More tasks if any to be executed.
     */
    public ParallelTask(Task first, Task second, Task... more) {
        list.add(first);
        list.add(second);
        if(more != null) {
            Collections.addAll(list, more);
        }
    }

    /**
     * Execute all the tasks in parallel. It returns <code>true</code> if all the tasks are executed, otherwise,
     * <code>false</code> is returned.
     *
     * @param context Workflow context
     * @return True/false
     */
    @Override
    public boolean execute(Context context) {
        List<Future<Boolean>> results = new ArrayList<>();
        list.forEach(w -> results.add(context.execute(w)));
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
