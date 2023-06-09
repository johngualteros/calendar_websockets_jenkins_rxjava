package com.app.calendar.tasks.controller;

import com.app.calendar.tasks.Task;
import com.app.calendar.tasks.rxJava.TaskService;
import com.app.calendar.tasks.rxJava.observer.TaskObserver;
import com.app.calendar.tasks.services.TaskRepository;
import com.app.calendar.tasks.webSockets.TaskWebSocketHandler;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/api/")
@RestController
@CrossOrigin(value = "*")

public class TaskController {

    @Autowired
    private TaskWebSocketHandler webSocketHandler;
    private final TaskRepository taskRepository;
    private final Validator validator;
    private final TaskService taskService;
    private Disposable subscription;

    @Autowired
    public TaskController(TaskRepository taskRepository, Validator validator, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.validator = validator;
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> getAll(){
        return taskRepository.findAll();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable String id) {
        Optional<Task> taskFound = taskRepository.findById(id);
        return ResponseEntity.of(Optional.of(taskFound));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Boolean> deleteTaskById(@PathVariable String id) {
        taskRepository.deleteById(id);
        Optional<Task> taskDeleted = taskRepository.findById(id);
        return ResponseEntity.ok(taskDeleted.isEmpty());
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createUser(@RequestBody Task task) {
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        taskService.addTask(task);
        // enviar mensaje a través de WebSocket
        try {
            webSocketHandler.sendMessage("new task added: " + task.getName());
        } catch (IOException e) {
            // manejar excepción si no se pudo enviar el mensaje
            System.out.println("no pude enviar mensaje a sesiones debido a: " + e.getMessage());
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/subscribe")
    public String subscribeToTasks() {
        Observable<Task> taskObservable = taskService.getTaskObservable();
        taskObservable.subscribe(new TaskObserver());
        return "Suscripción creada";
    }

    @GetMapping("/unsubscribe")
    public String unsubscribeFromTasks() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        return "Suscripción cancelada";
    }

}
