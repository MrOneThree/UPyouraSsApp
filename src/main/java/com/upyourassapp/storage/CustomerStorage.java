package com.upyourassapp.storage;

import com.upyourassapp.model.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kirill Popov
 */
public class CustomerStorage implements Storage<Customer> {
//  Storage layer. Simple in memory storage. No SQL or some fancy shit.
//  For new Storages, don't forget to implement Storage interface (if in doubt - google why).
    private static CustomerStorage customerStorage; //Singleton
    private final Map<Integer, Customer> customerMap = new HashMap<>(); //Simple map key(int) = id, value - customer
    private Integer counter = 0;    //Id counter. This is singleton, so counter keeps itself fairly correct. Pls don't multithread...

    private CustomerStorage() { //Singleton
    }

    public static CustomerStorage getInstance() {   //Singleton
        if (Objects.isNull(customerStorage)) {
            customerStorage = new CustomerStorage();
        }
        return customerStorage;
    }

    @Override
    public Map<Integer, Customer> list() {
        return new HashMap<>(customerMap); //The base storage has to be immutable, so list() returns new map. Customers are mutable tho
    }

    @Override
    public Integer put(Customer customer) {
        counter++;
        customerMap.put(counter, customer);
        return counter;
    }

    @Override
    public Customer update(Integer id, Customer customer) { //I don't use this in service layer. You can experiment if you want.
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
