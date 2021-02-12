package com.example.smartvotingsystem.services;

import com.example.smartvotingsystem.entity.StatementGuest;
import org.springframework.stereotype.Service;
import rx.Single;

import java.util.List;

@Service
public interface StatementGuestServices {
    List<Integer> findById(String statementId);

    Single<Double> getMean (String statementId);

    Single<Double> getMedian (String statementId);

    Single<Integer> getMode (String statementId);
}
