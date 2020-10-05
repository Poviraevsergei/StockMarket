package by.itechart.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {
        "companyStockes", "observers"
})
@EqualsAndHashCode(exclude = {
        "companyStockes", "observers"
})
@Entity
@Table
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String ticker;

    @Column
    private String industry;

    @Column
    private String phone;

    @Column
    private String url;

    @Column
    private String logo;

    @JsonManagedReference
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, orphanRemoval = true)
    private final Set<Stock> companyStockes = new HashSet<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "userCompanies")
    private final Set<User> observers = new HashSet<>();

    public Company() {
    }

    public Company(Long id, String name, String ticker, String industry, String phone, String url, String logo) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.industry = industry;
        this.phone = phone;
        this.url = url;
        this.logo = logo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Stock> getCompanyStockes() {
        return companyStockes;
    }

    public Set<User> getObservers() {
        return observers;
    }
}