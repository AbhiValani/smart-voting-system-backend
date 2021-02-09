package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.repository.StatementRepository;
import com.example.smartvotingsystem.services.StatementServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;

@Service
public class StatementServicesImpl implements StatementServices {

    @Autowired
    StatementRepository statementRepository;

    @Override
    public Single<Statement> save(Statement statement) {
        return addStatementToRepository(statement);
    }

    private Single<Statement> addStatementToRepository(Statement statement) {
        return Single.create(singleSubscriber -> {
            Statement newStatement = statementRepository.save(statement);
            singleSubscriber.onSuccess(newStatement);
            System.out.println("Statement Created");
        });
    }
}
