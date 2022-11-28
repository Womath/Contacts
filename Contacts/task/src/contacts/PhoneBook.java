package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhoneBook {

    private enum ContactType {
        PERSON,
        ORGANIZATION;

        ContactType() {
        }
    }
    private enum Action {
        ADD,
        REMOVE,
        EDIT,
        COUNT,
        INFO,
        EXIT;

        Action() {
        }
    }

    private Scanner scanner = new Scanner(System.in);
    private List<Contact> contacts;
    private Field field;
    private ContactType contactType;

    private enum Field {
        NAME,
        SURNAME,
        BIRTHDATE,
        GENDER,
        ADDRESS,
        NUMBER;

        Field() {
        }
    }

    public PhoneBook() {
        this.contacts = new ArrayList<>();
    }

    public void menu() {
        while (true) {
            System.out.println("Enter action(add, remove, edit, count, info, exit):");
            try {
                Action action = Action.valueOf(scanner.nextLine().toUpperCase());
                switch (action) {
                    case ADD -> addContact();
                    case REMOVE -> removeContact(selectContact());
                    //case EDIT -> editContact(selectContact());
                    case COUNT -> System.out.println("The Phone Book has " + contacts.size() + " records.\n");
                    case INFO -> printContactInfo(selectContact());
                    case EXIT -> System.exit(0);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("This is not a valid action! " +
                        "Please choose from this list: add, remove, edit, count, info, exit\n");
            }
        }
    }

    private void addContact() {
        selectContactType();
        if (contactType == ContactType.PERSON) {
            addPerson();
            System.out.println("The record was added.\n");
        } else if (contactType == ContactType.ORGANIZATION) {
            addOrganization();
            System.out.println("The record was added.\n");
        }
    }

    private void addPerson() {
        System.out.println("Enter the name of the person:");
        String name = scanner.nextLine();
        System.out.println("Enter the surname of the person:");
        String surname = scanner.nextLine();
        System.out.println("Enter the birth date:");
        String birthDate = validateBirthDate(scanner.nextLine());
        System.out.println("Enter the gender (M, F):");
        String gender = validateGender(scanner.nextLine());
        System.out.println("Enter the number:");
        String phoneNumber = scanner.nextLine();

        contacts.add(new Person(true, name, surname, birthDate, gender, phoneNumber));
    }

    private void addOrganization() {
        System.out.println("Enter the organization name: ");
        String orgName = scanner.nextLine();
        System.out.println("Enter the address: ");
        String address = scanner.nextLine();
        System.out.println("Enter the number:");
        String phoneNumber = scanner.nextLine();

        contacts.add(new Organization(false, orgName, address, phoneNumber));
    }

    private void removeContact(Contact contact) {
        if (contact != null) {
            contacts.remove(contact);
            System.out.println("The record was removed!\n");
        } else {
            System.out.println("No records to remove!\n");
        }
    }

    private void editContact(Contact contact) {
        if (contact != null) {
            if (contact.isPerson()) {
                Person person = (Person) contact;
                editPerson(person);
            } else {
                Organization org = (Organization) contact;
                editOrganization(org);
            }
            contact.setLastEditTime(LocalDateTime.now());
        } else {
            System.out.println("No records to edit!\n");
        }
    }

    private void editPerson(Person person) {
        System.out.println("Select a field (name, surname, number, birthdate, gender):");
        try {
            this.field = Field.valueOf(scanner.nextLine().toUpperCase());
            switch (field) {
                case NAME -> {
                    System.out.println("Enter name:");
                    person.setName(scanner.nextLine());
                }
                case SURNAME -> {
                    System.out.println("Enter surname:");
                    person.setSurname(scanner.nextLine());
                }
                case NUMBER -> {
                    System.out.println("Enter number:");
                    person.setPhoneNumber(scanner.nextLine());
                }
                case BIRTHDATE -> {
                    System.out.println("Enter birthdate:");
                    person.setBirthDate(validateBirthDate(scanner.nextLine()));
                }
                case GENDER -> {
                    System.out.println("Enter gender:");
                    person.setGender(validateGender(scanner.nextLine()));
                }
                default -> System.out.println("Please choose from this list: name, surname, number\n");
            }
            System.out.println("The record was updated!\n");
        } catch (IllegalArgumentException e) {
            System.out.println("This is not a valid field! " +
                    "Please choose from this list: name, surname, number\n");
        }
    }

    private void editOrganization(Organization org) {
        System.out.println("Select a field (address, number):");
        try {
            this.field = Field.valueOf(scanner.nextLine().toUpperCase());
            switch (field) {
                case ADDRESS -> {
                    System.out.println("Enter name:");
                    org.setAddress(scanner.nextLine());
                }
                case NUMBER -> {
                    System.out.println("Enter number:");
                    org.setPhoneNumber(scanner.nextLine());
                }
                default -> System.out.println("Please choose from this list: address, number\n");
            }
            System.out.println("The record was updated!\n");
        } catch (IllegalArgumentException e) {
            System.out.println("This is not a valid field! " +
                    "Please choose from this list: address, number\n");
        }
    }

    private Contact selectContact() {
        if (contacts.isEmpty()) {
            return null;
        } else {
            listContacts();
            System.out.println("Select a record:");
            int recordIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            return contacts.get(recordIndex);
        }
    }

    private void selectContactType() {
        System.out.println("Enter the type (person, organization): ");
        try {
            this.contactType = ContactType.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("This is not a valid contact type! " +
                    "Please choose from this list: person, organization\n");
        }
    }

    private void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("The Phone Book is empty!\n");
        } else {
            for (Contact contact : contacts) {
                if (contact.isPerson()) {
                    Person person = (Person) contact;
                    System.out.println((contacts.indexOf(contact) + 1) + ". " + person.getName() +
                            " " + person.getSurname());
                } else {
                    Organization org = (Organization) contact;
                    System.out.println((contacts.indexOf(contact) + 1) + ". " + org.getOrgName());
                }
            }
        }
    }

    private void printContactInfo(Contact contact) {
        if (contact != null) {
            if (contact.isPerson()) {
                Person person = (Person) contact;
                printPersonInfo(person);
            } else {
                Organization org = (Organization) contact;
                printOrganizationInfo(org);
            }
        }
        System.out.println();
    }

    private void printPersonInfo(Person person) {
        System.out.println("Name: " + person.getName());
        System.out.println("Surname: " + person.getSurname());
        System.out.println("Birth date: " + person.getBirthDate());
        System.out.println("Gender: " + person.getGender());
        System.out.println("Number: " + person.getPhoneNumber());
        System.out.println("Time created: " + person.getCreationTime());
        System.out.println("Time last edit: " + person.getLastEditTime());
    }

    private void printOrganizationInfo(Organization org) {
        System.out.println("Organization name: " + org.getOrgName());
        System.out.println("Address: " + org.getAddress());
        System.out.println("Number: " + org.getPhoneNumber());
        System.out.println("Time created: " + org.getCreationTime());
        System.out.println("Time last edit: " + org.getLastEditTime());
    }

    public String validateGender(String gender) {
        if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
            return gender;
        } else {
            System.out.println("Invalid gender!");
            return "[no data]";
        }
    }

    public String validateBirthDate(String birthDate) {
        try {
            LocalDate date = LocalDate.parse(birthDate);
            return date.toString();
        } catch (Exception e) {
            System.out.println("Invalid birth date!");
            return "[no data]";
        }
    }

}
