package org.render.Task.service;

import org.render.Task.DTO.TaskDTO;
import org.render.Task.entity.Task;
import org.render.Task.repository.TaskRepository;
import org.render.TaskStatus;
import org.render.User.entity.User;
import org.render.User.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TaskService {

    TaskRepository taskRepository;
    UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void createNewTask(TaskDTO taskDTO, Long userId) {

        User foundUserInDB = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Task newTask = Task.newBuilder()
                .setName(taskDTO.getName())
                .setDescription(taskDTO.getDescription())
                .setStatus(TaskStatus.RENDERING)
                .setDateTime(LocalDateTime.now())
                .setUser(foundUserInDB)
                .build();

        taskRepository.save(newTask);
    }

    public void checkTimeToCompleteTask(List<Task> listOfCurrentTasks) {
        for (Task task : listOfCurrentTasks) {
            if (LocalDateTime.now().isAfter(task.getCreatingDate().plusMinutes(3))) {
                completeTask(task);
            }
        }
    }

    public void completeTask(Task taskToComplete) {
        taskToComplete.setCompletingDate(taskToComplete.getCreatingDate().plusMinutes(3));
        taskToComplete.setStatus(TaskStatus.COMPLETED);

        taskRepository.save(taskToComplete);
    }
}
