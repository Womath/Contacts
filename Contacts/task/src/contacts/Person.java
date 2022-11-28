package contacts;

import java.lang.reflect.Field;

public class Person extends Contact {
    private String name;
    private String surname;
    private String birthDate;
    private String gender;

    public Person(boolean isPerson, String name, String surname, String birthDate, String gender, String phoneNumber) {
        super(isPerson, phoneNumber);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;;
        this.gender = gender;
    }

    @Override
    public Field[] getAllFields() {
        return Person.class.getDeclaredFields();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
