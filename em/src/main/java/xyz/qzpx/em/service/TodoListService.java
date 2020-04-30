package xyz.qzpx.em.service;

import xyz.qzpx.em.dataObject.TodoListDO;

import java.util.List;

public interface TodoListService {
    void add(TodoListDO todoListDO);

    void del(TodoListDO todoListDO);


    List<TodoListDO> getAll();

    void update(TodoListDO todoListDO);
}
