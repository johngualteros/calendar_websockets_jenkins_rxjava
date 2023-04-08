package com.app.calendar.tasks.guava;

import com.app.calendar.tasks.Task;
import com.app.calendar.tasks.guava.listener.TaskListener;
import com.app.calendar.tasks.services.TaskRepository;
import com.google.common.eventbus.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor

public class TaskServiceGuava {
    private final EventBus eventBus = new EventBus();
    private final TaskRepository taskRepository;

    public void addTask(Task task) {
        // add the task to db
        taskRepository.save(task);
        eventBus.post(task);
    }

    public void registerListener(TaskListener listener) {
        eventBus.register(listener);
    }

    public void unregisterListener(TaskListener listener) {
        eventBus.unregister(listener);
    }
}

