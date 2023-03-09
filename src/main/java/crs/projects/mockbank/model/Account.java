package crs.projects.mockbank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    private Double balance;

    private Boolean isActive;

    private Long userId;
}
