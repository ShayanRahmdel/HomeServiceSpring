package com.shayanr.HomeServiceSpring.entity.business;



import com.shayanr.HomeServiceSpring.entity.users.Customer;
import com.shayanr.HomeServiceSpring.exception.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String comment;


    private Integer score;

    @OneToOne(mappedBy = "comment")
    private CustomerOrder customerOrder;

    @ManyToOne()
    private Customer customer;

    public void setScore(Integer score) {
        if (score<=1 || score>=5){
            throw new ValidationException("Score must be between 1 and 5");
        }
        this.score=score;
    }

}
