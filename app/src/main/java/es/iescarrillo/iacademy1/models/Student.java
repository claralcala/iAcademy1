package es.iescarrillo.iacademy1.models;

import java.time.LocalDate;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "student")
public class Student extends Person{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long   id;

    @ColumnInfo(name = "dni")
    private String dni;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "familyPhone")
    private String familyPhone;
    @ColumnInfo(name = "birthdate")
    private LocalDate birthdate;

    public Student() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", phone='" + phone + '\'' +
                ", familyPhone='" + familyPhone + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }
}
