package by.itechart.model.domain;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"company"})
@EqualsAndHashCode(exclude = {"company"})
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "company")
    private Company company;

    @Column
    private String openPrice;

    @Column
    private String closePrice;

    @Column
    private String ticker;

    @Column
    private LocalDate date;

    @Column
    private String annualDividends;

    @Column
    private String lowestAnnualPrice;

    @Column
    private String highestAnnualPrice;
}