package com.shayanr.HomeServiceSpring.controller;


import com.shayanr.HomeServiceSpring.dto.*;
import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;
import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.OrderStatus;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.entity.users.User;
import com.shayanr.HomeServiceSpring.mapper.CustomerMapper;
import com.shayanr.HomeServiceSpring.mapper.ExpertMapperCustom;
import com.shayanr.HomeServiceSpring.mapper.OrderMapper;
import com.shayanr.HomeServiceSpring.mapper.UserMapper;
import com.shayanr.HomeServiceSpring.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;
    private final ExpertMapperCustom expertMapperCustom;


    @PostMapping("/createCategory")
    public ResponseEntity<DutyCategory> createDutyCategory(@RequestBody DutyCategory dutyCategory) {
        return new ResponseEntity<>(adminService.createDutyCategory(dutyCategory), HttpStatus.CREATED);
    }

    @PostMapping("/createSubDuty/{categoryId}")
    public ResponseEntity<SubDuty> createSubDuty(@RequestBody SubDuty subDuty, @PathVariable Integer categoryId) {
        return new ResponseEntity<>(adminService.createSubDuty(subDuty, categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/see-category")
    public List<DutyCategory> seeDutyCategories() {
        return adminService.seeAllDutyCategories();
    }

    @GetMapping("/see-subduties")
    public List<SubDuty> seeSubDuties() {
        return adminService.seeAllSubDuty();
    }

    @GetMapping("/see-subduty-by-category/{category}")
    public List<SubDuty> seeSubDutyByCategory(@PathVariable Integer category) {
        return adminService.seeSubDutyByCategory(category);
    }

    @GetMapping("/see-customers")
    public List<Customer> seeCustomers() {
        return adminService.seeAllCustomer();
    }

    @GetMapping("/see-experts")
    public List<Expert> seeExperts() {
        return adminService.seeAllExpert();
    }

    @PutMapping("/update-category/{category}")
    public void updateCategory(@PathVariable Integer category, @RequestBody String newTitle) {
        adminService.updateDutyCategory(category, newTitle);

    }

    @DeleteMapping("/delete-customer/{customerId}")
    public void deleteCustomer(@PathVariable Integer customerId ){
        adminService.removeCustomer(customerId);
    }

    @DeleteMapping("/delete-expert/{experId}")
    public void deleteExpert(@PathVariable Integer experId){
        adminService.removeExpert(experId);
    }

    @PutMapping("/update-subduty/{category}")
    public void updateSubDuty(@PathVariable Integer category, @RequestBody SubDutyDto subDutyDto) {
        adminService.updateSubDuty(category, subDutyDto.getNewTitle(),
                subDutyDto.getDescription(), subDutyDto.getBasePrice());
    }

    @DeleteMapping("/delete-subduty/{id}")
    public void deleteSubDuty(@PathVariable Integer id) {
        adminService.removeSubDuty(id);
    }

    @DeleteMapping("/delete-category/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        adminService.removeDutyCategory(id);
    }

    @PutMapping("/confirm-expert/{id}")
    public void confirmExpert(@PathVariable Integer id) {
        adminService.confirmExpert(id);
    }

    @PostMapping("/add-epxert-to-subduty/{expertId}/{subdutyid}")
    public void addExpertToSubDuty(@PathVariable Integer expertId, @PathVariable Integer subdutyid) {
        adminService.addExpertInSubDuty(expertId, subdutyid);
    }

    @DeleteMapping("/remove-expert-from-subduty/{expertId}/{subdutyId}")
    public void removeExpertFromSubduty(@PathVariable Integer expertId, @PathVariable Integer subdutyId) {
        adminService.removeExpertFromSubDuty(expertId, subdutyId);
    }

    @GetMapping("/search-user")
    public List<UserDto> searchAdminByUser(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String subDutyTitle,
            @RequestParam(required = false) Double minRate,
            @RequestParam(required = false) Double maxRate,
            @RequestParam(required = false) LocalTime registerFrom,
            @RequestParam(required = false) LocalTime registerTo
            ) {
        List<User> users = adminService.searchAdminByUser(firstName, lastName, email, subDutyTitle, minRate, maxRate,registerFrom,registerTo);
        return userMapper.userToExpertResponse(users);

    }
    @GetMapping("/search-orders")
    public List<OrderResponseDto> searchOrders(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subDuty){

        return OrderMapper.INSTANCE.listModelToResponse(adminService.searchOrders(startDate, endDate,orderStatus,category,subDuty));

    }

    @GetMapping("/search-suggests-by-name")
    public List<WorkSuggestion> searchSuggestionsByName(@RequestParam String firstName,@RequestParam String lastName){
        return adminService.searchWorkSuggestionByName(firstName,lastName);
    }

    @GetMapping("/search-suggest-by-count")
    public List<ExpertResponseDto> searchExpertByCount(@RequestParam Integer desiredCount){
         return expertMapperCustom.listModelToResponse(adminService.searchExpertByCountSuggest(desiredCount));
    }

    @GetMapping("/search-customer-by-order-count")
    public List<CustomerResponseDto> searchCustomersByOrderCount(@RequestParam Integer desiredCount) {
        return CustomerMapper.INSTANCE.listModelToResponse(adminService.searchCustomerByCountOrder(desiredCount));
    }

    @GetMapping("/see-orders-by-fullname")
    public List<OrderResponseDto> seeOrderByFullName(@RequestParam String firstName,@RequestParam String lastName){
        return OrderMapper.INSTANCE.listModelToResponse(adminService.seeOrdersByFullName(firstName,lastName));
    }

}
