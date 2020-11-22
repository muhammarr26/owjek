package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity(tableName = "owride_history")
public class OWRIDEModel {

    public static final int PICKUP = 0;
    public static final int TRANSPORT = 1;
    public static final int ARRIVED = 2;
    public static final int CANCELLED = 3;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "owridehistory_id")
    private int id;

//    @ColumnInfo(name = "date")
//    private Date date;

    @ColumnInfo(name = "from")
    private String from;

    @ColumnInfo(name = "to")
    private String to;

    @ColumnInfo(name = "status")
    private int status;

//    @ColumnInfo(name = "fare")
//    private int fare;

    public OWRIDEModel(String from, String to) {
//        this.date = new Date();
        this.from = from;
        this.to = to;
//        this.fare = fare;
        this.status = PICKUP;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public int getFare() {
//        return fare;
//    }
//
//    public void setFare(int fare) {
//        this.fare = fare;
//    }
}
