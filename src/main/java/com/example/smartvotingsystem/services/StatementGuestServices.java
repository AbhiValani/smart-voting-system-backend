package com.example.smartvotingsystem.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatementGuestServices {
    List<Integer> findById(String statementId);

    double getMean (String statementId);

    double getMedian (String statementId);

    int getMode (String statementId);
}
