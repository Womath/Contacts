package contacts;

import java.lang.reflect.Field;

public class Organization extends Contact {
    private final String orgName;
    private String address;

    public Organization(boolean isPerson, String orgName, String address, String phoneNumber) {
        super(isPerson, phoneNumber);
        this.orgName = orgName;
        this.address = address;
    }

    @Override
    public Field[] getAllFields() {
        return Organization.class.getDeclaredFields();
    }

    public String getOrgName() {
        return orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
