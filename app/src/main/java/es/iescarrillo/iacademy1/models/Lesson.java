package es.iescarrillo.iacademy1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity (tableName = "lesson")
public class Lesson {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    protected long id;

    @ColumnInfo(name="lessonDate")
    private LocalDate lessonDate;
    @ColumnInfo(name="lessonHour")
    private LocalTime lessonHour;

    public Lesson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(LocalDate lessonDate) {
        this.lessonDate = lessonDate;
    }

    public LocalTime getLessonHour() {
        return lessonHour;
    }

    public void setLessonHour(LocalTime lessonHour) {
        this.lessonHour = lessonHour;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", lessonDate=" + lessonDate +
                ", lessonHour=" + lessonHour +
                '}';
    }
}
