package com.app.calendar.tasks.rxJava.observer;

import com.app.calendar.tasks.Task;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class TaskObserver implements Observer<Task> {

    @Override
    public void onSubscribe(Disposable d) {
        // No se utiliza en este ejemplo
    }

    @Override
    public void onNext(Task task) {
        System.out.println("New added task: " + task.getName());
    }

    @Override
    public void onError(Throwable e) {
        System.err.println("new error found: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        // No se utiliza en este ejemplo
    }
}

