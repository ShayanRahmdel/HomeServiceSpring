package com.shayanr.HomeServiceSpring.controller;

import com.shayanr.HomeServiceSpring.dto.CustomerResponseDto;
import com.shayanr.HomeServiceSpring.dto.ExpertResponseDto;
import com.shayanr.HomeServiceSpring.dto.SubDutyDto;
import com.shayanr.HomeServiceSpring.entity.business.DutyCategory;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.entity.users.Expert;
import com.shayanr.HomeServiceSpring.mapper.CustomerMapper;
import com.shayanr.HomeServiceSpring.mapper.ExperMapperCustom;
import com.shayanr.HomeServiceSpring.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ExperMapperCustom experMapperCustom;


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

    @GetMapping("/search-expert")
    public List<ExpertResponseDto> searchAdminByExpert(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String subDutyTitle,
            @RequestParam(required = false) Double minRate,
            @RequestParam(required = false) Double maxRate
    ) {
        List<Expert> experts = adminService.searchAdminByExpert(firstName, lastName, email, subDutyTitle, minRate, maxRate);
        List<ExpertResponseDto> expertResponseDtos = experMapperCustom.listModelToResponse(experts);
        setExpertSubduty(experts, expertResponseDtos);
        return expertResponseDtos;
    }

    @GetMapping("/search-customer")
    public List<CustomerResponseDto> searchAdminByCustomer(@RequestParam(required = false) String firstName,
                                                           @RequestParam(required = false) String lastName,
                                                           @RequestParam(required = false) String email) {
        List<Customer> customers = adminService.searchAdminByCustomer(firstName, lastName, email);
        return CustomerMapper.INSTANCE.listModelToResponse(customers);

    }

    private void setExpertSubduty(List<Expert> experts, List<ExpertResponseDto> expertResponseDtos) {
        for (ExpertResponseDto expert : expertResponseDtos) {
            for (Expert modelExpert : experts) {
                Set<SubDuty> subDuties = modelExpert.getSubDuties();
                for (SubDuty subDuty : subDuties) {
                    if (subDuty.getTitle() != null) {
                        expert.setSubDutyTitle(subDuty.getTitle());
                    }
                }

            }
        }
    }
}
