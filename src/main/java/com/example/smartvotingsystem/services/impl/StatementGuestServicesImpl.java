package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.StatementGuest;
import com.example.smartvotingsystem.repository.StatementGuestRepository;
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
        return statistics.getMean(findById(statementId));
    }

    @Override
    public double getMedian(String statementId) {
        return statistics.getMedian(findById(statementId));
    }

    @Override
    public int getMode(String statementId) {
       return statistics.getMode(findById(statementId));
    }
}
