package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.business.*;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.service.*;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceImplTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private DutyCategoryService dutyCategoryService;
    @Autowired
    private SubDutyService subDutyService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WorkSuggestionService workSuggestionService;
    @Autowired
    private AddressService addressService;
    private final File imageFile = new File("C:\\Users\\Shayan\\Desktop\\IMG-20201021-WA0005.jpg");

    @Test
    void signUp() {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        assertEquals(customer,customerService.signUp(customer));
    }

    @Test
    void changePassword() {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        Customer customer1 = customerService.changePassword(customer.getId(), "Farzad1!", "Farzad1!");
        assertEquals("Farzad1!",customer1.getPassword());
    }

    @Test
    void createOrder() {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 1), LocalTime.of(10, 10, 10));
        assertEquals(customerOrder,customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId()));

    }

    @Test
    void createAddress() {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 1), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId());
        Address address = new Address("tehran","tehran","sabalan","1615795517");
        assertEquals(address,customerService.createAddress(address,customer.getId(),customerOrder.getId()));


    }

    @Test
    void seeSuggestionsByPrice() throws IOException {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 2), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId());
        Address address = new Address("tehran","tehran","sabalan","1615795517");
        customerService.createAddress(address,customer.getId(),customerOrder.getId());
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        adminService.confirmExpert(expert.getId());
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 100.0, "3days");
        WorkSuggestion workSuggestion1 = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 200.0, "3days");
        expertService.createSuggest(workSuggestion,customerOrder.getId(),expert.getId());
        expertService.createSuggest(workSuggestion1,customerOrder.getId(),expert.getId());


        List<WorkSuggestion> workSuggestions = customerService.seeSuggestionsByPrice(customer.getId());
        WorkSuggestion firstworkSuggestion = workSuggestions.get(0);


        assertEquals(100.0, firstworkSuggestion.getSuggestedPrice());


    }

    @Test
    void seeSuggestionsByExpertScore() throws IOException {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 2), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId());
        Address address = new Address("tehran","tehran","sabalan","1615795517");
        customerService.createAddress(address,customer.getId(),customerOrder.getId());
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        Expert expert1 = new Expert("Majid", "Alavi", "majid@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        expertService.signUp(expert1,imageFile);
        expertService.signUp(expert, imageFile);
        expert.setOverallScore(10.0);
        expert1.setOverallScore(5.0);
        expertService.save(expert);
        expertService.save(expert1);
        adminService.confirmExpert(expert.getId());
        adminService.confirmExpert(expert1.getId());
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 100.0, "3days");
        WorkSuggestion workSuggestion1 = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 200.0, "3days");
        expertService.createSuggest(workSuggestion,customerOrder.getId(),expert.getId());
        expertService.createSuggest(workSuggestion1,customerOrder.getId(),expert1.getId());

        List<WorkSuggestion> workSuggestions = customerService.seeSuggestionsByPrice(customer.getId());
        WorkSuggestion firstworkSuggestion = workSuggestions.get(0);

        assertEquals(10.0,firstworkSuggestion.getExpert().getOverallScore());
    }



    @Test
    void acceptSuggest() throws IOException {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        adminService.confirmExpert(expert.getId());
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 2), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId());
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 100.0, "3days");
        expertService.createSuggest(workSuggestion,customerOrder.getId(),expert.getId());

        CustomerOrder customerOrder1 = customerService.acceptSuggest(workSuggestion.getId());
        assertEquals(OrderStatus.WAITING_FOR_THE_SPECIALIST_TO_COME,customerOrder1.getOrderStatus());


    }

    @Test
    void updateOrderToBegin() throws IOException {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        adminService.confirmExpert(expert.getId());
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 2), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId());
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 100.0, "3days");
        expertService.createSuggest(workSuggestion,customerOrder.getId(),expert.getId());
        customerService.acceptSuggest(workSuggestion.getId());
        CustomerOrder customerOrder1 = customerService.updateOrderToBegin(customerOrder.getId(), workSuggestion.getId(), LocalDate.of(2025, 1, 4));
        assertEquals(OrderStatus.WORK_BEGIN,customerOrder1.getOrderStatus());

    }

    @Test
    void updateOrderToEnd() throws IOException {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        adminService.confirmExpert(expert.getId());
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 2), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder,category.getId(),subDuty.getId(),customer.getId());
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 100.0, "3days");
        expertService.createSuggest(workSuggestion,customerOrder.getId(),expert.getId());
        customerService.acceptSuggest(workSuggestion.getId());
        customerService.updateOrderToBegin(customerOrder.getId(), workSuggestion.getId(), LocalDate.of(2025, 1, 4));
        CustomerOrder customerOrder1 = customerService.updateOrderToEnd(customerOrder.getId());

        assertEquals(OrderStatus.WORK_DONE,customerOrder1.getOrderStatus());
    }


    @AfterEach
    void deleteAll() {
        workSuggestionService.deleteAll();
        orderService.deleteAll();
        addressService.deleteAll();
        subDutyService.deleteAll();
        dutyCategoryService.deleteAll();
        customerService.deleteAll();
        expertService.deleteAll();
    }
}