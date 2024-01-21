package com.shayanr.HomeServiceSpring.entity.business;




import com.shayanr.HomeServiceSpring.entity.users.Expert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubDuty  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double basePrice;

    @ManyToMany
    private Set<Expert> experts;

    @ManyToOne(fetch = FetchType.EAGER)
    private DutyCategory dutyCategory;

    @Override
    public String toString() {
        return "SubDuty id "+ getId() +"\n"+
                "description " + description +"\n"+
                "basePrice " + basePrice + "\n"+
                "========================="+"\n";
    }


}
