package com.doan1.mpec_restaurant.onClick_interface;

import com.doan1.mpec_restaurant.object.Customer;

public interface IClickCardCustomerListener {
    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);
}
