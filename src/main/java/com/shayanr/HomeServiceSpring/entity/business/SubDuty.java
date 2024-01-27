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
    private String title;


    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double basePrice;

    @ManyToMany
    @JoinTable(
            name = "subduty_expert",
            joinColumns = @JoinColumn(name = "subduty_id"),
            inverseJoinColumns = @JoinColumn(name = "expert_id")
    )
    private Set<Expert> experts;

    @ManyToOne(fetch = FetchType.EAGER)
    private DutyCategory dutyCategory;


    public SubDuty(String title, String description, Double basePrice) {
        this.title = title;
        this.description = description;
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "SubDuty id "+ getId() +"\n"+
                "description " + description +"\n"+
                "basePrice " + basePrice + "\n"+
                "========================="+"\n";
    }


}
