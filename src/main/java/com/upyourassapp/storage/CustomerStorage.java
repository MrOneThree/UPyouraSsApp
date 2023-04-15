package com.upyourassapp.storage;

import com.upyourassapp.model.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kirill Popov
 */
public class CustomerStorage implements Storage<Customer> {

    private static CustomerStorage customerStorage;
    private final Map<Integer, Customer> customerMap = new HashMap<>();
    private Integer counter = 0;

    private CustomerStorage() {
    }

    public static CustomerStorage getInstance() {
        if (Objects.isNull(customerStorage)) {
            return new CustomerStorage();
        }
        return customerStorage;
    }

    @Override
    public Map<Integer, Customer> list() {
        return new HashMap<>(customerMap);
    }

    @Override
    public Integer put(Customer customer) {
        counter++;
        customerMap.put(counter, customer);
        return counter;
    }

    @Override
    public Customer update(Integer id, Customer customer) {
        customerMap.put(id, customer);
        return customerMap.get(id);
    }

    @Override
    public Customer fetchById(Integer id) {
        return customerMap.get(id);
    }

    @Override
    public Boolean delete(Integer id) {
        customerMap.remove(id);
        return true;
    }
}
