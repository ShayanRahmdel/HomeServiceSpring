package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.business.*;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.service.*;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
class ExpertServiceImplTest {
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
    void signUp() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        assertEquals(expert, expertService.signUp(expert, imageFile));

    }

    @Test
    void changePassword() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Expert expert1 = expertService.changePassword(expert.getId(), "Farzad1@", "Farzad1@");

        assertEquals("Farzad1@", expert1.getPassword());
    }

    @Test
    void seeOrder() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 10, 1), LocalTime.of(10, 10, 10));
        CustomerOrder customerOrder1 = new CustomerOrder(300.0, "clean",
                LocalDate.of(2024, 10, 1), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder, category.getId(), subDuty.getId(), customer.getId());
        customerService.createOrder(customerOrder1, category.getId(), subDuty.getId(), customer.getId());
        adminService.confirmExpert(expert.getId());
        adminService.addExpertInSubDuty(expert.getId(), subDuty.getId());

        List<CustomerOrder> customerOrders = expertService.seeOrder(expert.getId());

        assertEquals(2, customerOrders.size());
    }

    @Test
    void saveImage() throws IOException {
        String path = "F:\\HomeServiceSpring\\src\\main\\java\\com\\shayanr\\HomeServiceSpring\\image.jpg";
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        expertService.saveImage(path, expert.getId());

        File imageFile = new File(path);
        assertTrue(imageFile.exists(), "Image file should exist");
    }

    @Test
    void createSuggest() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 1), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder, category.getId(), subDuty.getId(), customer.getId());
        adminService.confirmExpert(expert.getId());
        adminService.addExpertInSubDuty(expert.getId(), subDuty.getId());
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 2),
                LocalTime.of(10, 10, 10), 110.0, "3days");


        assertEquals(workSuggestion, expertService.createSuggest(workSuggestion, customerOrder.getId(), expert.getId()));
    }

    @Test
    void signUpWithVideo() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        File image = new File("C:\\Users\\Shayan\\Desktop\\IMG-20201021-WA0005.mp4");

        assertThrows(PersistenceException.class, () -> {
            expertService.signUp(expert, image);
        });
    }

    @Test
    void changePasswordWithNotvalidpass() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);

        assertThrows(PersistenceException.class, () -> {
            expertService.changePassword(expert.getId(), "123", "123");
        });
    }

    @Test
    void notAcceptedExpertSeeOrder() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean",
                LocalDate.of(2025, 1, 1), LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder, category.getId(), subDuty.getId(), customer.getId());

        assertThrows(PersistenceException.class, () -> {
            expertService.seeOrder(expert.getId());
        });
    }

    @Test
    void saveImageWithEmptyPath() throws IOException {
        String path = "";
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);


        File imageFile = new File(path);
        assertThrows(NullPointerException.class, () -> {
            expertService.saveImage(path, expert.getId());
        });
    }

    @Test
    void createSuggestWithWrongDate() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean", LocalDate.of(2025, 1, 3),
                LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder, category.getId(), subDuty.getId(), customer.getId());
        adminService.confirmExpert(expert.getId());
        adminService.addExpertInSubDuty(expert.getId(), subDuty.getId());
        //day is one day before
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 3),
                LocalTime.of(9, 10, 10), 110.0, "3days");

        assertThrows(PersistenceException.class, () -> {
            expertService.createSuggest(workSuggestion, customerOrder.getId(), expert.getId());
        });
    }

    @Test
    void createSuggestLessThanBasePrice() throws Exception {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com", "Shayan1@", LocalDate.now(),
                LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com", "Shayan1@",
                LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        CustomerOrder customerOrder = new CustomerOrder(200.0, "clean", LocalDate.of(2025, 1, 3),
                LocalTime.of(10, 10, 10));
        customerService.createOrder(customerOrder, category.getId(), subDuty.getId(), customer.getId());
        adminService.confirmExpert(expert.getId());
        adminService.addExpertInSubDuty(expert.getId(), subDuty.getId());

        //price less than base price
        WorkSuggestion workSuggestion = new WorkSuggestion(LocalDate.of(2025, 1, 4),
                LocalTime.of(9, 10, 10), 50.0, "3days");

        assertThrows(PersistenceException.class, () -> {
            expertService.createSuggest(workSuggestion, customerOrder.getId(), expert.getId());
        });
    }

    @AfterEach
    void delete() {
        workSuggestionService.deleteAll();
        orderService.deleteAll();
        subDutyService.deleteAll();
        dutyCategoryService.deleteAll();
        customerService.deleteAll();
        expertService.deleteAll();
    }
}