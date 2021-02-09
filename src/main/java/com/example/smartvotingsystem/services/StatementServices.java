package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.entity.Statement;
import org.springframework.stereotype.Service;
import rx.Single;

@Service
public interface StatementServices {
    Single<Statement> save(Statement statement);
}
