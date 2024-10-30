package org.example.expert.domain.common.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonValidator {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public Todo validateTodoExists(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));
    }

    public User validateUserExists(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new InvalidRequestException("User not found"));
    }

}
