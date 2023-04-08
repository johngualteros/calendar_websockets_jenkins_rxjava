package com.app.calendar.tasks.rxJava.logger;

import com.app.calendar.tasks.Task;
import com.app.calendar.tasks.rxJava.TaskService;
import com.app.calendar.tasks.rxJava.observer.TaskObserver;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class TaskLogger {

    private Disposable subscription;

    public void startLogging(TaskService taskService) {
        Observable<Task> taskObservable = taskService.getTaskObservable();
        taskObservable.subscribe(new TaskObserver());
    }

    public void stopLogging() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

}
