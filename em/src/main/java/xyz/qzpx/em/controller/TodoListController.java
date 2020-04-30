package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.TodoListDO;
import xyz.qzpx.em.service.TodoListService;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoListController {

    @Autowired
    private TodoListService todoListService;

    @PostMapping("/add")
    public TodoListDO add(@RequestBody TodoListDO todoListDO) {
        todoListService.add(todoListDO);
        return todoListDO;
    }

    @PostMapping("/del")
    public void del(@RequestBody TodoListDO todoListDO) {
        todoListService.del(todoListDO);
    }

    @GetMapping("/all")
    public List<TodoListDO> getAll() {
        List<TodoListDO> all = todoListService.getAll();
        return all;
    }

    @PostMapping("/update")
    public void update(@RequestBody TodoListDO todoListDO) {
        todoListService.update(todoListDO);
    }

}
