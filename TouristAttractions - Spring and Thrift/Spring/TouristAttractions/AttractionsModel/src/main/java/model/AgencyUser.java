package model;

import java.util.Objects;

public class AgencyUser extends Entity<Integer>{
    private String userName;
    private String password;

    public AgencyUser(int id, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.setId(id);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgencyUser that = (AgencyUser) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public String toString() {
        return "AgencyUser{" +
                "name='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
