package com.app.calendar.tasks.guava.logger;

import com.app.calendar.tasks.Task;
import com.app.calendar.tasks.guava.listener.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class TaskLogger implements TaskListener {
    @Override
    public void onTaskAdded(Task task) {
        System.out.println("n\n\n\n\nnew task added: " + task.getName());
    }
}

