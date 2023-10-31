package es.iescarrillo.iacademy1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity (tableName = "lesson", foreignKeys = {@ForeignKey(entity= Course.class, parentColumns = "id",
        childColumns = "course_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity= Classroom.class, parentColumns = "id",
                childColumns = "classroom_id", onDelete = ForeignKey.CASCADE)})
public class Lesson {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    protected long id;

    @ColumnInfo(name="lessonDate")
    private LocalDate lessonDate;
    @ColumnInfo(name="lessonHour")
    private LocalTime lessonHour;

    @ColumnInfo(name="course_id")
    private long course_id;

    @ColumnInfo(name="classroom_id")
    private long classroom_id;

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

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public long getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(long classroom_id) {
        this.classroom_id = classroom_id;
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
