package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.dto.BaseWebResponse;
import com.example.smartvotingsystem.entity.Statement;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.List;

@Service
public interface StatementServices {
    Single<Statement> save(Statement statement);
    Single<List<Statement>> getStatements(String roomId);
    void setNull();

    Single<Statement> getCurrentStatement(String roomId);
}
