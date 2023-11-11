package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.daos.AdminDAO;
import es.iescarrillo.iacademy1.daos.ClassroomDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Lesson;

public class ClassRoomService implements ClassroomDAO {


    private ClassroomDAO classroomDAO;

    public ClassRoomService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        classroomDAO= db.classroomDAO();
    }
    @Override
    public long insertClassroom(Classroom classroom) {
        return classroomDAO.insertClassroom(classroom);

    }

    @Override
    public void updateClassroom(Classroom classroom) {
        classroomDAO.updateClassroom(classroom);
    }

    @Override
    public void deleteClassroom(Classroom classroom) {
        classroomDAO.deleteClassroom(classroom);
    }

    @Override
    public List<Classroom> getAll() {
        return classroomDAO.getAll();
    }

    @Override
    public Map<Classroom, List<Lesson>> getClassroomWithLessonsMap() {
        return classroomDAO.getClassroomWithLessonsMap();
    }

    @Override
    public List<Classroom> getClassroomsByAcademy(long academyid) {
        return classroomDAO.getClassroomsByAcademy(academyid);
    }

    @Override
    public void deleteClassById(long class_id, long ac_id) {
        classroomDAO.deleteClassById(class_id, ac_id);
    }
}
