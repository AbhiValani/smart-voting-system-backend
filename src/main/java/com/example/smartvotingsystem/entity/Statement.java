package com.example.smartvotingsystem.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("statement")
public class Statement {
    @Id
    private String statementId = UUID.randomUUID().toString();

    @Indexed
    private String roomId;
    private String statementText;
    private boolean isCurrent;
    private double avgScore;
    private double medianScore;

    @Override
    public String toString() {
        return "Statement{" +
                "statementId='" + statementId + '\'' +
                ", roomId='" + roomId + '\'' +
                ", statementText='" + statementText + '\'' +
                ", isCurrent=" + isCurrent +
                ", avgScore=" + avgScore +
                ", medianScore=" + medianScore +
                '}';
    }
}
