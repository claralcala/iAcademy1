package es.iescarrillo.iacademy1.models;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public abstract class Person {


    @Embedded
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "user=" + user +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
