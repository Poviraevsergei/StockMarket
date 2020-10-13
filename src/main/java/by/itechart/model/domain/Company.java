package by.itechart.model.domain;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.Id;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Table
@Entity
@ToString(exclude = {
        "companyStockes", "observers"
})
@EqualsAndHashCode(exclude = {
        "companyStockes", "observers"
})
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {

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
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Stock> companyStockes = new HashSet<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "userCompanies")
    private Set<User> observers = new HashSet<>();
}