package codeview.apps.dndcallblocker.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Time;

/**
 * Created by Wijay on 4/10/2018.
 */
@Entity(tableName = "blacklist")
public class BlacklistModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;
    @NonNull
    private String phone;

    public BlacklistModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
