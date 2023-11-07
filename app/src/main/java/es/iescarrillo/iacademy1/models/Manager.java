package es.iescarrillo.iacademy1.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "manager")
public class Manager extends Person{

    @ColumnInfo(name="dni")
    private String dni;

    @ColumnInfo(name="phone")
    private String phone;

    public Manager(){
        super();
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

    @Override
    public String toString() {
        return "Manager{" +
                "dni='" + dni + '\'' +
                ", phone='" + phone + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}