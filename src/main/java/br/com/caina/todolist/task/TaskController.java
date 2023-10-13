package br.com.caina.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caina.todolist.util.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaksRepository taksRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt())) {
            return ResponseEntity.badRequest().body("Data de inicio não pode ser menor que a data atual!");
        }

        taskModel.setUserId((UUID) request.getAttribute("userId"));
        
        var task = this.taksRepository.save(taskModel);
        return ResponseEntity.status(200).body(taskModel);
    }

    @GetMapping("/buscar") // GET
    public List<TaskModel> tasks(HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        return taksRepository.findByUserId((UUID) userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity altera(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        var tasks = this.taksRepository.findById(id).orElse(null);

        if (tasks == null) {
            return ResponseEntity.badRequest().body("Tarefa não encontrada!");
        }
        
        //orElse (var tasks) permite buscar por getUserId... get...)
        if (!tasks.getUserId().equals(userId)) {
            return ResponseEntity.badRequest().body("Usuário não tem permissão de alterar essa tarefa!");
        }
        
        Utils.copyNullProperties(taskModel, tasks);
        taskModel.setUserId((UUID) userId);
        taskModel.setId(id);
        return ResponseEntity.ok().body(this.taksRepository.save(taskModel));
    }
}
