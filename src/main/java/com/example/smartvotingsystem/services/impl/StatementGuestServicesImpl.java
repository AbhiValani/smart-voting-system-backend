package com.example.smartvotingsystem.services.impl;

import com.example.smartvotingsystem.entity.StatementGuest;
import com.example.smartvotingsystem.repository.StatementGuestRepository;
import com.example.smartvotingsystem.services.StatementGuestServices;
import com.example.smartvotingsystem.statistics.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Single;
import rx.schedulers.Schedulers;
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
        List<Integer> list = new ArrayList<>();
        for (StatementGuest statementGuest : statementGuestList){
            list.add(statementGuest.getScore());
        }
        return list;
    }

    @Override
    public Single<Double> getMean(String statementId) {
        return Single. <Double> create(
                singleSubscriber -> {
                   double mean =  statistics.getMean(findById(statementId));
                   singleSubscriber.onSuccess(mean);
                }
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Double> getMedian(String statementId) {
        return Single. <Double> create(
                singleSubscriber -> {
                    double median = statistics.getMedian(findById(statementId));
                    singleSubscriber.onSuccess(median);
                }
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Integer> getMode(String statementId) {
        return Single. <Integer> create(
                singleSubscriber -> {
                    int mode = statistics.getMode(findById(statementId));
                    singleSubscriber.onSuccess(mode);
                }
        ).subscribeOn(Schedulers.io());
    }
}
