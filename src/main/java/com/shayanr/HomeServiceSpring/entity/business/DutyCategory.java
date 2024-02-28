package com.shayanr.HomeServiceSpring.entity.business;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DutyCategory  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false,unique = true)
    private String title;

    @OneToMany(mappedBy = "dutyCategory")
    @JsonIgnore
    private List<SubDuty> subDuties;



    @Override
    public String toString() {
        return " DutyCategory id " + getId()+"\n" +
                "  title " + title+"\n"+
                "==================";
    }


}
