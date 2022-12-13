package com.example.decapay.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 09/12/2022 - 5:46 PM
 * @project DECAPAY
 */

@Getter
@Setter
@Entity
@Table(name = "budget_tb")
public class Budget extends BaseEntity{

    private String title;
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Timestamp
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Timestamp
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<LineItem> lineItems;

    @ManyToOne
    @JoinColumn(name = "user_tb_id")
    private User user;
}
