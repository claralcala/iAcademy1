package es.iescarrillo.iacademy1.models;


import androidx.room.Embedded;
import androidx.room.Relation;

public class LessonWithClassroom {
    @Embedded
    private Lesson lesson;

    @Relation(parentColumn = "classroom_id", entityColumn = "id")
    private Classroom classroom;

    public LessonWithClassroom(){
        //Constructor por defecto
    }
    public Lesson getLesson(){
        return lesson;
    }

    public void setLesson(Lesson lesson){
        this.lesson = lesson;
    }

    public Classroom getClassroom(){
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
