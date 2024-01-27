package com.shayanr.HomeServiceSpring.service.impl;

import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.service.*;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminServiceImplTest {

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
    private WorkSuggestionService workSuggestionService;
    @Autowired
    private OrderService orderService;
    private final File imageFile = new File("C:\\Users\\Shayan\\Desktop\\IMG-20201021-WA0005.jpg");

    @Test
    void createDutyCategory() {
        DutyCategory category = new DutyCategory("cleanig");

        assertEquals(category, adminService.createDutyCategory(category));
    }

    @Test
    void createSubDuty() {
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);


        assertEquals(subDuty, adminService.createSubDuty(subDuty, category.getId()));
    }

    @Test
    void seeAllCustomer() {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        List<Customer> customers = adminService.seeAllCustomer();
        assertEquals(1, customers.size());

    }

    @Test
    void seeAllExpert() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        List<Expert> experts = adminService.seeAllExpert();
        assertEquals(1, experts.size());
    }

    @Test
    void seeAllDutyCategories() {
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        List<DutyCategory> dutyCategories = adminService.seeAllDutyCategories();

        assertNotNull(dutyCategories);
        assertEquals(1, dutyCategories.size());
    }

    @Test
    void seeAllSubDuty() {
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        List<SubDuty> subDuties = adminService.seeAllSubDuty();

        assertNotNull(subDuties);
        assertEquals(1, subDuties.size());
    }

    @Test
    void confirmExpert() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        Expert expert1 = adminService.confirmExpert(expert.getId());
        assertEquals(expert1.getConfirmation(), Confirmation.ACCEPTED);
    }


    @Test
    void addExpertInSubDuty() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        adminService.confirmExpert(expert.getId());
        Expert expert1 = adminService.addExpertInSubDuty(expert.getId(), subDuty.getId());

        Set<SubDuty> subDuties = expert1.getSubDuties();

        assertEquals(1, subDuties.size());
    }


    @Test
    void removeExpertFromSubDuty() throws IOException {
        Expert expert = new Expert("Ali", "Alavi", "hasan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(10, 0, 0));
        expertService.signUp(expert, imageFile);
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        adminService.confirmExpert(expert.getId());
        Expert expert1 = adminService.addExpertInSubDuty(expert.getId(), subDuty.getId());

        Expert expert2 = adminService.removeExpertFromSubDuty(expert1.getId(), subDuty.getId());

        Set<SubDuty> subDuties = expert2.getSubDuties();

        assertEquals(0, subDuties.size());
    }

    @Test
    void removeCustomer() {
        Customer customer = new Customer("Shayan", "Rahmdel", "shayan@gmail.com",
                "Shayan1@", LocalDate.now(), LocalTime.of(12, 0, 0));
        customerService.signUp(customer);
        adminService.removeCustomer(customer.getId());

        List<Customer> customers = adminService.seeAllCustomer();
        assertEquals(0, customers.size());

    }


    @Test
    @Order(12)
    void updateSubDuty() {
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        SubDuty subDuty1 = adminService.updateSubDuty(subDuty.getId(), "repair", "repair tv", 222.0);

        assertEquals("repair", subDuty1.getTitle());

    }

    @Test
    void seeSubDutyByCategory() {
        DutyCategory category = new DutyCategory("cleanig");
        DutyCategory category1 = new DutyCategory("repair");
        adminService.createDutyCategory(category);
        adminService.createDutyCategory(category1);
        SubDuty subDuty1 = new SubDuty("repair tv", "repair", 200.0);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());
        adminService.createSubDuty(subDuty1, category1.getId());
        List<SubDuty> subDuties = adminService.seeSubDutyByCategory(category1.getId());

        assertEquals(1, subDuties.size());
    }

    @Test
    void updateSubDutyWithDuplicate() {
        DutyCategory category = new DutyCategory("cleanig");
        adminService.createDutyCategory(category);
        SubDuty subDuty = new SubDuty("home cleaning", "cleaning", 100.0);
        adminService.createSubDuty(subDuty, category.getId());


        assertThrows(PersistenceException.class, () -> {
            adminService.updateSubDuty(subDuty.getId(), "home cleaning", "cleaning", 100.0);
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