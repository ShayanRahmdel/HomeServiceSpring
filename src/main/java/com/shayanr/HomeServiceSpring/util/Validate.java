package com.shayanr.HomeServiceSpring.util;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;
@Component
public class Validate {


    public static Boolean isValidPostalCode(String postalCode) {
        Pattern pattern = Pattern.compile("^(?!(\\d)\\1{3})[13-9]{4}[1346-9][ -]?[013-9]{5}$|^$");
        return postalCode.matches(pattern.pattern());
    }
    public static Boolean isValidCity(String city) {
        Pattern pattern = Pattern.compile("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$");
        return city.matches(pattern.pattern());
    }

    public static Boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8}$");
        return password.matches(pattern.pattern());
    }

    public static Boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$");
        return name.matches(pattern.pattern());
    }

    public static Boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        return email.matches(pattern.pattern());
    }

    public static Boolean passwordValidation(String password) {

        if (!Validate.isValidPassword(password)) {
            System.out.println("enter valid password");
            return false;
        }
        return true;
    }

    public static Boolean nameValidation(String name) {
        if (!Validate.isValidName(name)) {
            System.out.println("enter valid name");
            return false;
        }
        return true;
    }

    public static Boolean emailValidation(String email) {

        if (!Validate.isValidEmail(email)) {
            System.out.println("enter valid email");
            return false;
        }
        return true;
    }

    public static Boolean postalValidation(String postal){
        if (!Validate.isValidPostalCode(postal)) {
            System.out.println("enter valid postal code");
            return false;
        }
        return true;
    }

    public static Boolean cityValidation(String city){
        if (!Validate.isValidCity(city)) {

            System.out.println("enter valid city code");
            return false;
        }
        return true;
    }


    public static Boolean isValidDateAndTime(LocalDate date, LocalTime time){

        if (date.isBefore(LocalDate.now()) ) {
            System.out.println("Your date is before");
            return false;
        }else if (date.equals(LocalDate.now())&& time.isBefore(LocalTime.now())){
            System.out.println("Your time is before");
            return false;
        }
        return true;
    }

    public static Boolean isValidDateAndTimeForSuggest(LocalDate date, LocalTime time, CustomerOrder order){

        if (date.isBefore(order.getWorkDate()) ) {
            System.out.println("Your date is before");
            return false;
        }else if (date.equals(order.getWorkDate())&& time.isBefore(order.getTimeDate())){
            System.out.println("Your time is before");
            return false;
        }
        return true;
    }
}
