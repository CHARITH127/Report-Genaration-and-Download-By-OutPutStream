package lk.epic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {


    @Id
    private String userId;

    @Column(unique = true)
    private String userName;
    private String address;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telephoneNumber;
    private String password;
    private String createDate;
    private String createUser;
    private String lastUpdateDate;
    private String lastUpdateUser;

    public Customer() {
    }

    public Customer(String userId, String userName, String address, String email, String telephoneNumber, String password) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setAddress(address);
        this.setEmail(email);
        this.setTelephoneNumber(telephoneNumber);
        this.setPassword(password);
    }

    public Customer(String userId, String userName, String address, String email, String telephoneNumber, String password, String createDate, String createUser, String lastUpdateDate, String lastUpdateUser) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setAddress(address);
        this.setEmail(email);
        this.setTelephoneNumber(telephoneNumber);
        this.setPassword(password);
        this.setCreateDate(createDate);
        this.setCreateUser(createUser);
        this.setLastUpdateDate(lastUpdateDate);
        this.setLastUpdateUser(lastUpdateUser);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
}
