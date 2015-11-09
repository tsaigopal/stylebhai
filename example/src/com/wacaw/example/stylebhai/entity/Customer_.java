package com.wacaw.example.stylebhai.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Customer_.class)
public class Customer_ {
    public static volatile SingularAttribute<Customer, String> name;
    public static volatile SingularAttribute<Customer, String> address;
    public static volatile SingularAttribute<Customer, Float> balance;
    public static volatile SingularAttribute<Customer, Integer> custId;
}
