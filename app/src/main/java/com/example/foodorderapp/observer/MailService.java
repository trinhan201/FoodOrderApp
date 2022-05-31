package com.example.foodorderapp.observer;

import java.util.ArrayList;
import java.util.List;

public class MailService {
    private List<CustomerEmailObserver> observers;

    public MailService() {
            this.observers = new ArrayList<CustomerEmailObserver>();
    }

    public void registerObserver(CustomerEmailObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CustomerEmailObserver observer) {
        int i = this.observers.indexOf(observer);
        if (i >= 0) {
            this.observers.remove(i);
        }
    }

    public void Notify() {
        for (CustomerEmailObserver observer : observers) {
            observer.sendEmail();
        }
    }
}
