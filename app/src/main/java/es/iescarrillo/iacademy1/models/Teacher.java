package es.iescarrillo.iacademy1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "teacher", foreignKeys = @ForeignKey(entity = Academy.class, parentColumns = "id",
childColumns = "academy_id", onDelete = ForeignKey.CASCADE))
public class Teacher extends Person{
    @ColumnInfo(name="dni")
    private String dni;

    @ColumnInfo(name="phone")
    private String phone;

    @ColumnInfo(name="address")
    private String address;

    @ColumnInfo(name="academy_id")
    private long academy_id;

    public Teacher() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(long academy_id) {
        this.academy_id = academy_id;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "dni='" + dni + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", academy_id=" + academy_id +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
