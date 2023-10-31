package es.iescarrillo.iacademy1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;


@Entity(tableName = "inscription", foreignKeys = {@ForeignKey(entity= Course.class, parentColumns = "id",
        childColumns = "course_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity= Student.class, parentColumns = "id",
                childColumns = "student_id", onDelete = ForeignKey.CASCADE)})
public class Inscription {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "registrationtime")
    private LocalDateTime registrationTime;

    @ColumnInfo(name="course_id")
    private long course_id;

    @ColumnInfo(name="student_id")
    private long student_id;
    public Inscription() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", registrationTime=" + registrationTime +
                '}';
    }
}
