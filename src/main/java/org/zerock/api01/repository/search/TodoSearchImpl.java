package org.zerock.api01.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api01.domain.QTodo;
import org.zerock.api01.domain.Todo;
import org.zerock.api01.dto.PageRequestDTO;
import org.zerock.api01.dto.TodoDTO;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch{

    public TodoSearchImpl() {
        super(Todo.class);
    }

    @Override
    public Page<TodoDTO> list(PageRequestDTO pageRequestDTO) {

        QTodo todo = QTodo.todo;

        JPQLQuery<Todo> jpqlQuery = from(todo);
        if (pageRequestDTO.getKeyword() != null){
            jpqlQuery.where(todo.title.contains(pageRequestDTO.getKeyword()));
        }

        if (pageRequestDTO.getFrom()!= null && pageRequestDTO.getTo() != null){
            jpqlQuery.where(todo.dueDate.between(pageRequestDTO.getFrom()
                    ,pageRequestDTO.getTo()));
        }

        if (pageRequestDTO.getCompleted() != null){
            jpqlQuery.where(todo.complete.eq(pageRequestDTO.getCompleted()));
        }

        JPQLQuery<TodoDTO> dtojpqlQuery = jpqlQuery.select(Projections.bean(TodoDTO.class,
                todo.tno,
                todo.title,
                todo.writer,
                todo.complete,
                todo.dueDate));

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
        pageRequestDTO.getSize(), Sort.by("tno").descending());


        this.getQuerydsl().applyPagination(pageable, dtojpqlQuery);

        List<TodoDTO> dtoList = dtojpqlQuery.fetch();

        long totalCount = dtojpqlQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, totalCount);

    }
}
