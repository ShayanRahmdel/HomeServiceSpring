package com.shayanr.HomeServiceSpring.entity.users;



import com.shayanr.HomeServiceSpring.entity.business.SubDuty;
import com.shayanr.HomeServiceSpring.entity.business.Wallet;
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
public class Expert extends User implements UserDetails {

    @Enumerated(EnumType.STRING)
    private Confirmation confirmation;

    private byte[] image;

    private Double overallScore=0.0;

    private boolean isEnabled;



    @ManyToMany(mappedBy = "experts")
    private Set<SubDuty> subDuties;

    @OneToMany(mappedBy = "expert")
    private List<WorkSuggestion> workSuggestions;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;
    @Override
    public String toString() {
        return  "Epert id " + getId()+"\n"+
                "Expert name " + getFirstName()+" "+ getLastName()+"\n"+
                "Expert email " + getEmail() +"\n"+
                "Expert password " + getPassword() +"\n"+
                "OverallScore  " + getOverallScore() +"\n";
    }
}
