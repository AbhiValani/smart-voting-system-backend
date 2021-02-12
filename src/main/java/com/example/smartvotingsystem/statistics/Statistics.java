package com.example.smartvotingsystem.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    double mean = 0 , median = 0;
    int mode = 0;

    public Statistics() {
    }

    public Statistics(double mean, double median, int mode) {
        this.mean = mean;
        this.median = median;
        this.mode = mode;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public int getMode() {
        return mode;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getMean(List<Integer> scoreList){
        if (scoreList.size() == 0){
            return 0;
        }
        double sum = 0;
        for (int value : scoreList){
            sum += value;
        }
        mean = sum / scoreList.size();
        setMean(mean);
        return mean;
    }
    public double getMedian(List<Integer> scoreList){
        int size = scoreList.size();
        double median = 0;
        if (size == 0)
            return 0;
        else if (size % 2 == 1){
            median = scoreList.get(size / 2 + 1);
        }
        else if (size % 2 == 0){
            int first = scoreList.get(size / 2);
            int second = scoreList.get(size / 2 + 1);
            median =  ((first + second) / 2);
        }
        setMedian(median);
        return median;
    }

    public int getMode (List<Integer> scoreList){
        int mode = 0 , frequency = 0;
        HashMap<Integer , Integer> hashMap = new HashMap<>();
        for (int i : scoreList){
            hashMap.put(i , hashMap.getOrDefault(i , 0) + 1);
        }
        int cnt = 0;
        for (Map.Entry<Integer , Integer> entry : hashMap.entrySet()){
            int key = entry.getKey();
            int value = entry.getValue();
            if (cnt == 0){
                mode = key;
                frequency = value;
            }else{
                if (frequency < value){
                    frequency = value;
                    mode = value;
                }
                else if (frequency == value){
                    double mean = getMean(scoreList);
                    if ((mode - mean) >= (key - mean)){
                        mode = key;
                    }
                }
            }
        }
        setMode(mode);
        return  mode;
    }

}
