package com.upyourassapp.service;

import com.upyourassapp.model.Customer;
import com.upyourassapp.storage.CustomerStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Kirill Popov
 */
public class CustomerService implements Service<Customer> {
//    Service layer. All the business logic must be implemented here.
//    For new Services, don't forget to implement Service interface (if in doubt - google why).

    private static CustomerService customerService; //Singleton.
    private final CustomerStorage storage;  //Simple autowiring...

    private CustomerService() {
        this.storage = CustomerStorage.getInstance();
    } //Singleton

    public static CustomerService getInstance() {   //Singleton
        if (Objects.isNull(customerService)) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    @Override
    public Map<Integer, Customer> list() {
        return storage.list();
    } //Get all items

    @Override
    public Customer find(Integer id) {
        checkIfExistsById(id);
        return storage.fetchById(id);
    }

    @Override
    public Integer save(Customer customer) {
        var errors = validate(customer);    //List of all validation errors to present at once.
        if (!errors.isEmpty()) {
            throw new RuntimeException(errors.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n")));    //Concatenate an list all errors
        }

        return storage.put(customer);
    }

    @Override
    public Customer update(Integer id, Customer customer) {
        checkIfExistsById(id);
        var errors = validate(customer);
        if (!errors.isEmpty()) {
            throw new RuntimeException(errors.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n")));
        }
        return storage.update(id, customer);
    }

    @Override
    public Boolean delete(Integer id) {
        checkIfExistsById(id);
        return storage.delete(id);
    }

    private void checkIfExistsById(Integer id) {
        if (Objects.isNull(storage.fetchById(id))) {
            throw new RuntimeException(String.format("Customer with id: %d does not exist.", id));
        }
    }

    private List<String> validate(Customer customer) {
        List<String> errors = new ArrayList<>();

        if (customer.getName().isEmpty()) {
            errors.add("Customer name cannot be empty!");
        }
        //Email RegEx (google it)
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher matcher = pattern.matcher(customer.getEmail());

        if (!matcher.find()) {
            errors.add("Invalid email");
        }

        return errors;
    }
}
