package es.iescarrillo.iacademy1.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "academy", foreignKeys = @ForeignKey(entity = Manager.class, parentColumns = "id",
        childColumns = "manager_id", onDelete = ForeignKey.CASCADE))

public class Academy {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;

    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="country")
    private String country;
    @ColumnInfo(name="state")
    private String state;
    @ColumnInfo(name="city")
    private String city;
    @ColumnInfo(name="address")
    private String address;
    @ColumnInfo(name="web")
    private String web;
    @ColumnInfo(name="email")
    private String email;
    @ColumnInfo(name="phone")
    private String phone;

    @ColumnInfo(name= "manager_id")
    private long manager_id;
    public Academy(){

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getManager_id() {
        return manager_id;
    }

    public void setManager_id(long manager_id) {
        this.manager_id = manager_id;
    }

    @Override
    public String toString() {
        return "Academy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", web='" + web + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
