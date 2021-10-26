package com.storedobject.workflow;

/**
 * Definition of a task.
 *
 * @author Syam
 */
public interface Task {

    /**
     * Execute the task.
     *
     * @param context Workflow context.
     * @return True if the task is executed successfully, otherwise false.
     */
    boolean execute(Context context);
}
