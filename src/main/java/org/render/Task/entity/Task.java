package org.render.Task.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.render.Role;
import org.render.TaskStatus;
import org.render.User.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    public static Task.TaskBuilder newBuilder() {
        return new Task().new TaskBuilder();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String description;
    TaskStatus status;
    LocalDateTime creatingDate;

    LocalDateTime closingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return id != null && Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Builder class
     */
    public class TaskBuilder {

        private TaskBuilder() {
        }

        public Task.TaskBuilder setName(String name) {
            Task.this.name = name;
            return this;
        }

        public Task.TaskBuilder setStatus(TaskStatus status) {
            Task.this.status = status;
            return this;
        }

        public Task.TaskBuilder setDescription(String description) {
            Task.this.description = description;
            return this;
        }

        public Task.TaskBuilder setDateTime(LocalDateTime creatingDate) {
            Task.this.creatingDate = creatingDate;
            return this;
        }

        public Task build() {
            return Task.this;
        }
    }
}
