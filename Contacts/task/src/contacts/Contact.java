package contacts;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact {
    private String phoneNumber;
    private final LocalDateTime creationTime;
    private LocalDateTime lastEditTime;
    private boolean isPerson;

    public Contact(boolean isPerson, String phoneNumber) {
        this.isPerson = isPerson;
        setPhoneNumber(phoneNumber);
        System.out.println("A record created!");
        this.creationTime = LocalDateTime.now();
        this.lastEditTime = LocalDateTime.now();
    }

    public abstract Field[] getAllFields();
    public String getPhoneNumber() {
        if (hasNumber()) {
            return phoneNumber;
        } else {
            return "[no number]";
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isValidNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "";
        }
    }

    public boolean isPerson() {
        return isPerson;
    }

    public boolean hasNumber() {
        return this.phoneNumber.length() > 0;
    }

    private boolean isValidNumber(String phoneNumber) {
        Pattern patter = Pattern.compile("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*");
        Matcher matcher = patter.matcher(phoneNumber);
        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("Wrong number format!");
            return false;
        }
    }

    public LocalDateTime getCreationTime() {
        return creationTime.minusSeconds(creationTime.getSecond()).minusNanos(creationTime.getNano());
    }

    public LocalDateTime getLastEditTime() {
        return lastEditTime.minusSeconds(lastEditTime.getSecond()).minusNanos(lastEditTime.getNano());
    }

    public void setLastEditTime(LocalDateTime lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

}
