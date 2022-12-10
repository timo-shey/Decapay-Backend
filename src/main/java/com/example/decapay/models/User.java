package com.example.decapay.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 09/12/2022 - 5:00 PM
 * @project DECAPAY
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_tb", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id"}),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "phone_number")
})
@SQLDelete(sql = "UPDATE user_tb SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User extends BaseEntity {

    @Column(name = "user_id")
    private String userId;
    private String firstname;
    private String lastname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BudgetCategory> categories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Budget> budgets;
}
