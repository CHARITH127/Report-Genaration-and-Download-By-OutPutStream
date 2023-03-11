package lk.epic.dto;

public class CustomerDTO {

    private String userId ;
    private String userName;
    private String address;
    private String email;
    private String telephoneNumber;
    private String password;

    public CustomerDTO() {
    }

    public CustomerDTO(String userId, String userName, String address, String email, String telephoneNumber, String password) {
        this.setUserId(userId);
        this.setUserName(userName);
        this.setAddress(address);
        this.setEmail(email);
        this.setTelephoneNumber(telephoneNumber);
        this.setPassword(password);
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
}
