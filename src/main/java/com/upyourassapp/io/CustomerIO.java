package com.upyourassapp.io;

import com.upyourassapp.model.Customer;
import com.upyourassapp.service.CustomerService;

import java.util.Objects;
import java.util.Scanner;

/**
 * @author Kirill Popov
 */
public class CustomerIO implements IO {

//    This is console IO for every implemented functional instance.
//    If you want to expand functionality with orders, routes, vehicles etc. this is the way to present it to users

    private static CustomerIO customerIO; //Singleton
    private final CustomerService service;  //Can't autowire, inject service layer.

    private CustomerIO() {
        this.service = CustomerService.getInstance();
    } //Singleton

    public static CustomerIO getInstance() {    //Singleton impl
        if (Objects.isNull(customerIO)) {
            customerIO = new CustomerIO();
        }
        return customerIO;
    }

    @Override
    public String getName() {
        return "Customers";
    }   //No spring - no beans. Has to be a pointer on exact IO from the list.

    @Override
    public void init() {
        System.out.println("/-------------------------------------/");
        System.out.println("Welcome to Customers data base!");
    }

    @Override
    public String requestInput() {
        System.out.println("To list all customers type \"List\", to fetch customer by id type \"Fetch\", " +
                "to add new customer type \"Add\", to delete customer type \"Delete\".");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void parseIO(String s) {
        switch (s.toLowerCase()) {
            case "add" -> {
                Customer newCustomer = new Customer();
                System.out.println("To add new customer let's fill required fields.");
                System.out.println("Please enter new customer's name: ");
                Scanner scanner = new Scanner(System.in);
                newCustomer.setName(scanner.nextLine());
                System.out.println("Please enter new customer's email: ");
                scanner = new Scanner(System.in);
                newCustomer.setEmail(scanner.nextLine());
                try {
                    System.out.printf("Customer successfully saved with id: %d\n", service.save(newCustomer));
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                }
            }
            case "list" -> {
                System.out.println("Here's the list of all customers:\n");
                service.list()
                        .forEach((key, value) -> System.out.printf("id: %d, name: %s, email: %s;\n", key, value.getName(), value.getEmail()));
            }
            case "fetch" -> {
                System.out.println("Enter customer id:");
                Scanner scanner = new Scanner(System.in);
                int id;

                try {
                    id = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.err.println("Input should be number");
                    return;
                }

                try {
                    Customer customer = service.find(id);
                    System.out.printf("id: %d, name: %s, email: %s\n", id, customer.getName(), customer.getEmail());
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                }
            }
            case "delete" -> {
                System.out.println("Enter customer id:");
                Scanner scanner = new Scanner(System.in);
                int id;

                try {
                    id = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.err.println("Input should be number");
                    return;
                }

                try {
                    service.delete(id);
                    System.out.printf("Customer with id: %d deleted successfully!\n", id);
                } catch (RuntimeException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
