package com.example.decapay.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * @author Ikechi Ucheagwu
 * @created 09/12/2022 - 5:09 PM
 * @project DECAPAY
 */

@Getter
@Setter
@Entity
@Table(name = "category_tb")
public class BudgetCategory extends BaseEntity{
    private String name;

    @ManyToOne()
    @JoinColumn(name = "user_tb_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "line_item_tb_id")
    private LineItem lineItem;
}
