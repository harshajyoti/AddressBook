package AddressBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddressBook {

    List<Contact> a = new ArrayList<>();

    static Scanner scan = new Scanner(System.in);
    public String firstName = "[A-Z]{1}[a-z]{2,20}";
    public String lastName = "[A-Z]{1}[a-z]{2,20}";
    private String address = "^[a-zA-Z0-9# ]+$";
    private String city = "[A-Z]{1}[a-z]{2,20}";
    private String state = "[A-Z]{1}[a-z]{2,20}";
    private String zip = "[0-9]{6}";
    private String phoneNum = "[0-9]{10}";
    private String email = "[a-z0-9]+@gmail.com$";

    public String validateInput(String fieldName, String regexPattern) {
        System.out.print("Enter " + fieldName + ": ");
        String input = scan.nextLine();

        Pattern pattern = Pattern.compile(regexPattern);
        if (input != null){
            while (!pattern.matcher(input).matches()) {
                System.out.println("Invalid input. Please enter a valid " + fieldName + ".");
                System.out.print("Enter " + fieldName + ": ");
                input = scan.nextLine();
            }
        }
        return input;

    }

    private void add(){
        Contact c = new Contact();

        c.setFirstName(validateInput("First name", firstName));
        System.out.println(c.getFirstName());

        c.setLastName(validateInput("Last name", lastName));
        System.out.println(c.getLastName());

        c.setAddress(validateInput("Address", address));
        System.out.println(c.getAddress());

        c.setCity(validateInput("City", city));
        System.out.println(c.getCity());

        c.setState(validateInput("State", state));
        System.out.println(c.getState());

        c.setZip(validateInput("Zip", zip));
        System.out.println(c.getZip());

        c.setPhoneNum(validateInput("Phone number", phoneNum));
        System.out.println(c.getPhoneNum());

        c.setEmail(validateInput("Email", email));
        System.out.println(c.getEmail());

        a.add(c);

    }

    private void displayContact(){
        for(Object contact:a){
//            System.out.println("----------------------------");
            System.out.println(contact.toString());
            System.out.println("----------------------------");
        }
    }

    private void modifyContact(){
        System.out.print("Enter Name of the person: ");
        String fName = scan.nextLine();
        for (Contact c:a){
            if (c.getFirstName().equalsIgnoreCase(fName)){
                System.out.println(c.getFirstName());
                System.out.println("1. First Name");
                System.out.println("2. Last Name");
                System.out.println("3. Address");
                System.out.println("4. City");
                System.out.println("5. State");
                System.out.println("6. zip");
                System.out.println("7. Phone Number");
                System.out.println("8. Email");
                System.out.println("Which detail do you want to modify");
                int option = scan.nextInt();
                switch (option){
                    case 1:
                        c.setFirstName(validateInput("First name", firstName));
                        System.out.println("Details modified.");
                        break;
                    case 2:
                        c.setLastName(validateInput("Last name", lastName));
                        System.out.println("Details modified.");
                        break;
                    case 3:
                        c.setAddress(validateInput("Address", address));
                        System.out.println("Details modified.");
                        break;
                    case 4:
                        c.setCity(validateInput("City", city));
                        System.out.println("Details modified.");
                        break;
                    case 5:
                        c.setState(validateInput("State", state));
                        System.out.println("Details modified.");
                        break;
                    case 6:
                        c.setZip(validateInput("Zip", zip));
                        System.out.println("Details modified.");
                        break;
                    case 7:
                        c.setPhoneNum(validateInput("Phone Number", phoneNum));
                        System.out.println("Details modified.");
                        break;
                    case 8:
                        c.setEmail(validateInput("Email", email));
                        System.out.println("Details modified.");
                        break;
                    default:
                        System.out.println("Invalid! Please choose the correct option");
                        break;
                }
            } else{
                System.out.println("Contact not found!");
            }
        }


//        else {
//            System.out.println(fName + " not found!");
//            System.out.print("Do you want to Add this contact(y or n): ");
//            String input = scan.next();
//            if (input.equalsIgnoreCase("y")){
//                AddressBook addressBook = new AddressBook();
//                addressBook.add();
//            } else {
//                System.out.println("not yet Implemented");
//            }
//        }

    }

    private void deleteContact(){
        System.out.print("Enter Name of the person: ");
        String fName = scan.nextLine();
        for (Contact c:a) {
            if (c.getFirstName().equalsIgnoreCase(fName)) {
                a.remove(c);
                System.out.println("Contact deleted");
                break;
            }
            else{
                System.out.println("Contact not found!");
            }
        }
    }

    public static void main(String[] args) {


        AddressBook addressBook = new AddressBook();

        // Display the options to the user
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Address book Application");
        while(true){
            System.out.println("1. Add a contact");
            System.out.println("2. Display contact");
            System.out.println("3. Modify a contact");
            System.out.println("4. Delete a contact");
            System.out.println("5. Exit");
            System.out.print("Please choose an option: ");
            int option = scan.nextInt();
            switch (option){
                case 1:
                    addressBook.add();
                    break;
                case 2:
                    addressBook.displayContact();
                    break;
                case 3:
                    addressBook.modifyContact();
                    break;
                case 4:
                    addressBook.deleteContact();
                    break;
                case 5:
                    System.out.println("Thank you.");
                    System.exit(0);
                default:
                    System.out.println("Choose a valid option!");

            }
        }
    }
}
