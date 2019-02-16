package com.norvera.confession.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "SIN_ACTIVE")
public class ExaminationActiveEntry extends ExaminationEntry {
    public ExaminationActiveEntry(@NonNull long _id,
                                  int commandmentId,
                                  int adult,
                                  int single,
                                  int married,
                                  int religious,
                                  int priest,
                                  int teen,
                                  int female,
                                  int male,
                                  int child,
                                  int customId,
                                  int count,
                                  String description) {
        super(_id, commandmentId, adult, single, married, religious, priest, teen, female, male, child, customId, description, count);
    }
}
