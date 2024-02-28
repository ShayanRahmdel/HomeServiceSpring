package com.shayanr.HomeServiceSpring.entity.users;



import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.WorkSuggestion;
import com.shayanr.HomeServiceSpring.entity.enumration.Confirmation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;



import java.util.List;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Expert extends User implements UserDetails {

    @Enumerated(EnumType.STRING)
    private Confirmation confirmation;

    private byte[] image;

    private Double overallScore=0.0;

    @ManyToMany(mappedBy = "experts")
    @JsonIgnore
    private Set<SubDuty> subDuties;

    @OneToMany(mappedBy = "expert")
    @JsonIgnore
    private List<WorkSuggestion> workSuggestions;

    @Override
    public String toString() {
        return  "Epert id " + getId()+"\n"+
                "Expert name " + getFirstName()+" "+ getLastName()+"\n"+
                "Expert email " + getEmail() +"\n"+
                "Expert password " + getPassword() +"\n"+
                "OverallScore  " + getOverallScore() +"\n";
    }
}
