package by.itechart.model.domain;

import lombok.Data;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;
import javax.persistence.Id;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@ToString(exclude = {
        "userCompanies"
})
@EqualsAndHashCode(exclude = {
        "userCompanies"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String role;

    @Column
    private String email;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column
    private Boolean deleted;

    @ManyToMany
    @JsonManagedReference
    @JsonIgnore
    @JoinTable(
            name = "user_company",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> userCompanies = new HashSet<>();
}