package org.zerock.api01.repository.search;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api01.domain.Todo;

public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch{

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public TodoSearchImpl() {
        super(Todo.class);
    }
}
