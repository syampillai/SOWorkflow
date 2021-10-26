package com.storedobject.workflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Two or more tasks to be executed sequentially. If any one of the tasks fails to execute, the rest of the tasks are
 * ignored.
 *
 * @author Syam
 */
public final class SerialTask implements Task {

    private final List<Task> list = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param first First task to be executed.
     * @param second Second task to be executed.
     * @param more More tasks if any to be executed.
     */
    public SerialTask(Task first, Task second, Task... more) {
        list.add(first);
        list.add(second);
        if(more != null) {
            Collections.addAll(list, more);
        }
    }

    /**
     * Execute the tasks sequentially. It returns <code>true</code> if all the tasks are executed. If any one of
     * the tasks fails to execute, the rest of the tasks are ignored and <code>false</code> is returned.
     *
     * @param context Workflow context
     * @return True/false
     */
    @Override
    public boolean execute(Context context) {
        for(Task w: list) {
            try {
                if(!w.execute(context)) {
                    return false;
                }
            } catch(Throwable error) {
                return false;
            } finally {
                context.predecessor = w;
            }
        }
        return true;
    }
}
