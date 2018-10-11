package codeview.apps.dndcallblocker.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "logs_table")
public class LogModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    @NonNull
    private String phone;
    @NonNull
    private String blockedTime;

    private boolean isSms;

    public static int count;

    public LogModel(String name,@NonNull String phone, @NonNull String blockedTime, boolean isSms) {
        this.name=name;
        this.phone = phone;
        this.blockedTime = blockedTime;
        this.isSms = isSms;
    }

    @Ignore
    public LogModel(@NonNull String phone, @NonNull String blockedTime, boolean isSms) {
        this.phone = phone;
        this.blockedTime = blockedTime;
        this.isSms=isSms;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(@NonNull String blockedTime) {
        this.blockedTime = blockedTime;
    }

    public boolean isSms() {
        return isSms;
    }

    public void setSms(boolean sms) {
        isSms = sms;
    }
}
