package com.app.calendar.tasks.rxJava;

import com.app.calendar.tasks.Task;
import com.app.calendar.tasks.services.TaskRepository;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    private List<Task> tasks = new ArrayList<>();
    private PublishSubject<Task> taskSubject = PublishSubject.create();

    public Task addTask(Task task) {
        taskRepository.save(task);
        taskSubject.onNext(task);
        return task;
    }

    public Observable<Task> getTaskObservable() {
        return taskSubject;
    }

}

