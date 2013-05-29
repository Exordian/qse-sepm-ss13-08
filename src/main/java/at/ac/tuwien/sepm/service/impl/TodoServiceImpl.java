package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.TodoDao;
import at.ac.tuwien.sepm.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoDao todoDao;




}
