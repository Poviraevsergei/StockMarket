package by.itechart.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@ToString(exclude = {
        "company"
})
@EqualsAndHashCode(exclude = {
        "company"
})
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private String annualDividends;

    @Column
    private String lowestAnnualPrice;

    @Column
    private String highestAnnualPrice;
}