package my.maven.spring.todo.controllers;

import my.maven.spring.todo.models.Task;
import my.maven.spring.todo.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/todo")
    public String todoMain(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "todo-main";
    }

    @GetMapping("/todo/add")
    public String todoAdd(Model model) {
        return "todo-add";
    }

    @PostMapping("/todo/add")
    public String todoTaskAdd(@RequestParam String title, @RequestParam String description, Model model) {
        Task task = new Task(title, description);
        taskRepository.save(task);
        return "redirect:/todo";
    }

    @GetMapping("/todo/{id}")
    public String todoDetails(@PathVariable(value = "id") long id, Model model) {
        if(!taskRepository.existsById(id)) {
            return "redirect:/todo";
        }

        Optional<Task> task = taskRepository.findById(id);
        ArrayList<Task> res = new ArrayList<>();
        task.ifPresent(res::add);
        model.addAttribute("task", res);
        return "todo-details";
    }

    @GetMapping("/todo/{id}/edit")
    public String todoEdit(@PathVariable(value = "id") long id, Model model) {
        if(!taskRepository.existsById(id)) {
            return "redirect:/todo";
        }

        Optional<Task> task = taskRepository.findById(id);
        ArrayList<Task> res = new ArrayList<>();
        task.ifPresent(res::add);
        model.addAttribute("task", res);
        return "todo-edit";
    }

    @PostMapping("/todo/{id}/edit")
    public String todoTaskUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                                 @RequestParam String description, Model model) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setTitle(title);
        task.setDescription(description);
        taskRepository.save(task);

        return "redirect:/todo";
    }

    @PostMapping("/todo/{id}/remove")
    public String todoTaskDelete(@PathVariable(value = "id") long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);

        return "redirect:/todo";
    }
}
