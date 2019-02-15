package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "SIN_ACTIVE")
public class ExaminationActiveEntry extends ExaminationEntry {
    public ExaminationActiveEntry(@NonNull long _id,
                                  String commandmentId,
                                  String adult,
                                  String single,
                                  String married,
                                  String religious,
                                  String priest,
                                  String teen,
                                  String female,
                                  String male,
                                  String child,
                                  String customId,
                                  String description) {
        super(_id, commandmentId, adult, single, married, religious, priest, teen, female, male, child, customId, description);
    }
}
