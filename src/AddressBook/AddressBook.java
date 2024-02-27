package AddressBook;
import Exception.CustomException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddressBook {

    HashMap<String, Contact> a = new HashMap();
    HashMap<String, ArrayList<String>> personByCity = new HashMap<>();
    HashMap<String, ArrayList<String>> personByState = new HashMap<>();

    static Scanner scan = new Scanner(System.in);
    public String firstName = "[A-Z]{1}[a-z]{2,20}";
    public String lastName = "[A-Z]{1}[a-z]{2,20}";
    private String address = "^[a-zA-Z0-9# ]+$";
    private String city = "[A-Z]{1}[a-z]{2,20}";
    private String state = "[A-Z]{1}[a-z]{2,20}";
    private String zip = "[0-9]{6}";
    private String phoneNum = "[0-9]{10}";
    private String email = "[a-z0-9]+@gmail.com$";

    public String validateInput(String fieldName, String regexPattern){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter " + fieldName + ": ");
        String input = scan.nextLine();

        Pattern pattern = Pattern.compile(regexPattern);
        while (!pattern.matcher(input).matches()) {
            System.out.println("Invalid input. Please enter a valid " + fieldName + ".");
            System.out.print("Enter " + fieldName + ": ");
            input = scan.nextLine();
        }
        return input;
    }

    // Check if the contact is there in the hashmap
    public Boolean contactExists(String fName){
        boolean contactExists = a.entrySet().stream()
                .anyMatch(entry -> entry.getKey().equals(fName));
        return contactExists;
    }

    private void add(StringBuilder stringBuilder, FileWriter writer) throws IOException {
        // Create a new Object of class Contact
        Contact c = new Contact();

        String fName = validateInput("First name", firstName);
        // Check if the contact is there in the address book
        if (!contactExists(fName)) {

            c.setFirstName(fName);
            System.out.println(c.getFirstName());

            c.setLastName(validateInput("Last name", lastName));
            System.out.println(c.getLastName());

            c.setAddress(validateInput("Address", address));
            System.out.println(c.getAddress());

            c.setCity(validateInput("City", city));
            System.out.println(c.getCity());
            ArrayList<String> personsInCity = personByCity.getOrDefault(c.getCity(), new ArrayList<>());
            personsInCity.add(fName);
            personByCity.put(c.getCity(), personsInCity);

            c.setState(validateInput("State", state));
            System.out.println(c.getState());
            ArrayList<String> personsInState = personByState.getOrDefault(c.getState(), new ArrayList<>());
            personsInState.add(fName);
            personByState.put(c.getState(), personsInState);

            c.setZip(validateInput("Zip", zip));
            System.out.println(c.getZip());

            c.setPhoneNum(validateInput("Phone number", phoneNum));
            System.out.println(c.getPhoneNum());

            c.setEmail(validateInput("Email", email));
            System.out.println(c.getEmail());

            // Add the content to CSV file
            StringBuilder sb = new StringBuilder();

            sb.append(c.getFirstName()).append(",").append(c.getLastName()).append(",").append(c.getAddress()).append(",").append(c.getCity()).append(",").append(c.getState()).append(",").append(c.getZip()).append(",").append(c.getPhoneNum()).append(",").append(c.getEmail()).append("\n");

            try {
                writer.append(sb.toString());
            } catch (IOException e) {
                System.out.println("Something went wrong!");
                e.printStackTrace();
            }
        } else{
            System.out.println("----------------------");
            System.out.println("Contact already exist!");
            System.out.println("----------------------");
        }
    }

    private void displayContact(){
        if (a.size() > 0){
            Scanner scan = new Scanner(System.in);

            System.out.println("-------------------------------------");
            System.out.println("Number of contact in different Cities");
            personByCity.entrySet().stream()
                    .map(entry -> entry.getKey() + " : " + entry.getValue().size())
                    .forEach(System.out::println);

            System.out.println("Number of contact in different States");
            personByState.entrySet().stream()
                    .map(entry -> entry.getKey() + " : " + entry.getValue().size())
                    .forEach(System.out::println);
            System.out.println("-------------------------------------");

            System.out.print("Do you want to search Contact by State or city [y or n]: ");
            String input = scan.next();
            if (input.equalsIgnoreCase("y")){
                System.out.println("1. City");
                System.out.println("2. State");
                System.out.println("-----------------------");
                System.out.print("Please select an option: ");
                int userInput = scan.nextInt();
                switch (userInput){
                    case 1:
                        System.out.print("Please enter City: ");
                        String city = scan.next();
                        if (personByCity.containsKey(city)) {
                            ArrayList<String> cityList = personByCity.get(city);
                            cityList.stream()
                                    .peek(name -> System.out.println("---------------------------"))
                                    .forEach(System.out::println);
                            System.out.println("---------------------------");
                        } else {
                            System.out.println("---------------------------");
                            System.out.println("No contact found in " + city);
                            System.out.println("---------------------------");
                        }
                        break;
                    case 2:
                        System.out.print("Please enter State: ");
                        String state = scan.next();
                        if (personByState.containsKey(state)) {
                            ArrayList<String> stateList = personByState.get(state);
                            stateList.stream()
                                    .peek(name -> System.out.println("---------------------------"))
                                    .forEach(System.out::println);
                            System.out.println("---------------------------");
                        } else {
                            System.out.println("---------------------------");
                            System.out.println("No contact found in " + state);
                            System.out.println("---------------------------");
                        }
                        break;
                    default:
                        System.out.println("---------------------------");
                        System.out.println("Please enter a valid option");
                        System.out.println("---------------------------");
                }
            } else{
                for (String name : a.keySet()){
                    System.out.println(a.get(name).toString());
                    System.out.println("---------------------------------");
                }
            }
        } else {
            System.out.println("---------------------------------");
            System.out.println("No contacts in the Address Book!");
            System.out.println("---------------------------------");
        }
    }

    private void modifyContact(){
        System.out.print("Enter Name of the person: ");
        String fName = scan.nextLine();

        // Check if the contact is there in the address book
        if (contactExists(fName)){
            // create an object of Contact class and assign the value to it
            Contact c = a.get(fName);

            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Address");
            System.out.println("4. City");
            System.out.println("5. State");
            System.out.println("6. zip");
            System.out.println("7. Phone Number");
            System.out.println("8. Email");
            System.out.print("Which detail do you want to modify: ");
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
            System.out.println("---------------------------------");
            System.out.println("Contact not found!");
            System.out.println("---------------------------------");
        }
    }

    private void deleteContact(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Name of the person: ");
        String fName = scan.nextLine();

        // If the name is there in the hashmap then delete the contact
        if (contactExists(fName)) {
            a.remove(fName);
            System.out.println("----------------");
            System.out.println("Contact removed!");
            System.out.println("----------------");
        } else{
            System.out.println("------------------");
            System.out.println("Contact not found!");
            System.out.println("------------------");
        }
    }

    public static void main(String[] args) throws CustomException {

        AddressBook addressBook = new AddressBook();

        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to the Address book Application");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("First Name").append(",").append("Last Name").append(",").append("Address").append(",").append("City").append(",").append("State").append(",").append("Zip").append(",").append("Phone Number").append(",").append("Email").append("\n");

        FileWriter writer = null;
        try {
            writer = new FileWriter("Data.csv");
            writer.write(stringBuilder.toString());
            System.out.println("File Created");
        } catch (IOException e) {
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }

        // Display the options to the user
        while(true){
            try {
                System.out.println("1. Add a contact");
                System.out.println("2. Display contact");
                System.out.println("3. Modify a contact");
                System.out.println("4. Delete a contact");
                System.out.println("5. Exit");
                System.out.println("-----------------------");
                System.out.print("Please choose an option: ");
                int option = scan.nextInt();
                switch (option){
                    case 1:
                        addressBook.add(stringBuilder, writer);
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
                        writer.close();
                        System.exit(0);
                    default:
                        System.out.println("----------------------");
                        System.out.println("Choose a valid option!");
                        System.out.println("----------------------");

                }
            } catch (Exception e){
                throw new CustomException("Please Select the valid option!");
            }
        }
    }
}
