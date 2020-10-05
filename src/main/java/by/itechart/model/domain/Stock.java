package by.itechart.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
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
import java.util.Date;

@ToString(exclude = {
        "company"
})
@EqualsAndHashCode(exclude = {
        "company"
})
@Entity
@Table
public class Stock {
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

    public Stock() {
    }

    public Stock(Long id, Company company, String openPrice, String closePrice, String ticker, Date date, String annualDividends, String lowestAnnualPrice, String highestAnnualPrice) {
        this.id = id;
        this.company = company;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.ticker = ticker;
        this.date = date;
        this.annualDividends = annualDividends;
        this.lowestAnnualPrice = lowestAnnualPrice;
        this.highestAnnualPrice = highestAnnualPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAnnualDividends() {
        return annualDividends;
    }

    public void setAnnualDividends(String annualDividends) {
        this.annualDividends = annualDividends;
    }

    public String getLowestAnnualPrice() {
        return lowestAnnualPrice;
    }

    public void setLowestAnnualPrice(String lowestAnnualPrice) {
        this.lowestAnnualPrice = lowestAnnualPrice;
    }

    public String getHighestAnnualPrice() {
        return highestAnnualPrice;
    }

    public void setHighestAnnualPrice(String highestAnnualPrice) {
        this.highestAnnualPrice = highestAnnualPrice;
    }
}