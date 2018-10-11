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

    public BlacklistModel(@NonNull String name, @NonNull String phone) {
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
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
}
