package com.app.calendar.tasks.rxJava;

import com.app.calendar.tasks.Task;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private List<Task> tasks = new ArrayList<>();
    private PublishSubject<Task> taskSubject = PublishSubject.create();

    public Task addTask(Task task) {
        tasks.add(task);
        taskSubject.onNext(task);
        return task;
    }

    public Observable<Task> getTaskObservable() {
        return taskSubject;
    }

}

