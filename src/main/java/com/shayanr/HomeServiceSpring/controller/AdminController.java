package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.dto.SubDutyDto;
import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

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
    public ResponseEntity<DutyCategory> updateCategory(@PathVariable Integer category ,@RequestBody String newTitle){
        return new ResponseEntity<>(adminService.updateDutyCategory(category,newTitle),HttpStatus.OK);
    }

    @PutMapping("/update-subduty/{category}")
    public ResponseEntity<SubDuty> updateSubDuty(@PathVariable Integer category , @RequestBody SubDutyDto subDutyDto){
        return new ResponseEntity<>
                (adminService.updateSubDuty(category,subDutyDto.getTitle(),
                        subDutyDto.getDescription(),subDutyDto.getBasePrice()),HttpStatus.OK);

    }
    @DeleteMapping("/delete-subduty/{id}")
    public void deleteSubDuty(@PathVariable Integer id){
        adminService.removeSubDuty(id);
    }

    @DeleteMapping("/delete-category/{id}")
    public void deleteCategory(@PathVariable Integer id){
        adminService.removeDutyCategory(id);
    }

    @PutMapping("/confirm-expert/{id}")
    public void confirmExpert(@PathVariable Integer id){
        adminService.confirmExpert(id);
    }

    @PostMapping("/add-epxert-to-subduty/{expertId}/{subdutyid}")
    public void addExpertToSubDuty(@PathVariable Integer expertId,@PathVariable Integer subdutyid){
        adminService.addExpertInSubDuty(expertId,subdutyid);
    }

    @PostMapping("/remove-expert-from-subduty/{expertId}/{subdutyId}")
    public void removeExpertFromSubduty(@PathVariable Integer expertId,@PathVariable Integer subdutyId){
        adminService.removeExpertFromSubDuty(expertId,subdutyId);
    }

}
