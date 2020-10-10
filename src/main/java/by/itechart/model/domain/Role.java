package by.itechart.model.domain;

import lombok.Data;

import java.util.Date;
import javax.persistence.Id;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Data
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isActive;

    @Column
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
}