package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.Statement;
import com.example.smartvotingsystem.entity.StatementGuest;
import com.example.smartvotingsystem.repository.StatementGuestRepository;
import com.example.smartvotingsystem.repository.StatementRepository;
import com.example.smartvotingsystem.services.StatementGuestServices;
import com.example.smartvotingsystem.statistics.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatementGuestServicesImpl implements StatementGuestServices {

    @Autowired
    StatementGuestRepository statementGuestRepository;

    @Autowired
    StatementRepository statementRepository;

    Statistics statistics = new Statistics();

    @Override
    public List<Integer> findById(String statementId) {
        List<StatementGuest> statementGuestList = statementGuestRepository.findByStatementId(statementId);
        System.out.println(statementGuestList);
        List<Integer> list = new ArrayList<>();
        for (StatementGuest statementGuest : statementGuestList){
            list.add(statementGuest.getScore());
        }
        System.out.println(list);
        return list;
    }

    @Override
    public double getMean(String statementId) {
        Statement statement = statementRepository.findByStatementId(statementId);
        double mean = statistics.getMean(findById(statementId));
        statement.setAvgScore(mean);
        statementRepository.deleteById(statement.getStatementId());
        Statement statement1 = statementRepository.save(statement);
        return mean;
    }

    @Override
    public double getMedian(String statementId) {
        return statistics.getMedian(findById(statementId));
    }

    @Override
    public int getMode(String statementId) {
       return statistics.getMode(findById(statementId));
    }

    @Override
    public List<StatementGuest> findByStatementId(String statementId) {
        return statementGuestRepository.findByStatementId(statementId);
    }
}
