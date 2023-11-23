package es.iescarrillo.iacademy1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "classroom", foreignKeys = @ForeignKey(entity = Academy.class, parentColumns = "id",
        childColumns = "academy_id", onDelete = ForeignKey.CASCADE))
public class Classroom {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="capacity")
    private Integer capacity;

    @ColumnInfo(name="academy_id")
    private long academy_id;
    public Classroom() {
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public long getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(long academy_id) {
        this.academy_id = academy_id;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
