package my.maven.spring.todo.repo;

import my.maven.spring.todo.models.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
