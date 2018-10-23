package codeview.apps.dndcallblocker.model;

public class Contact {
    private String name;
    private String phone;
    private boolean isBlacklisted;

    public Contact(String name, String phone, boolean isBlacklisted) {
        this.name = name;
        this.phone = phone;
        this.isBlacklisted = isBlacklisted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        isBlacklisted = blacklisted;
    }
}
