package xyz.qzpx.em.service.impl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.TodoListDOMapper;
import xyz.qzpx.em.dataObject.TodoListDO;
import xyz.qzpx.em.service.TodoListService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    private TodoListDOMapper todoListDOMapper;

    @Override
    public void add(TodoListDO todoListDO) {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        todoListDO.setUsername(username);
        todoListDO.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())));
        todoListDO.setStatus(false);
        todoListDOMapper.insertSelective(todoListDO);
    }

    @Override
    public void del(TodoListDO todoListDO) {
        todoListDOMapper.deleteByPrimaryKey(todoListDO.getId());
    }

    @Override
    public List<TodoListDO> getAll() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        List<TodoListDO> result = todoListDOMapper.selectByUsername(username);
        return result;
    }

    @Override
    public void update(TodoListDO todoListDO) {
        Boolean status = todoListDOMapper.selectByPrimaryKey(todoListDO.getId()).getStatus();
        todoListDO.setStatus(!status);
        todoListDOMapper.updateByPrimaryKeySelective(todoListDO);
    }
}
