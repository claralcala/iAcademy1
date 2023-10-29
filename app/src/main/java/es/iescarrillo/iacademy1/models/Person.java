package es.iescarrillo.iacademy1.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "person")
public abstract class Person {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    protected long id;

    @ColumnInfo(name="name")
    protected String name;

    @ColumnInfo(name="surname")
    protected String surname;

    @ColumnInfo(name="email")
    protected String email;

    public Person (){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
