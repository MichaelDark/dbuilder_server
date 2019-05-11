package com.mtemnohud.dbuilder.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtemnohud.dbuilder.model.request.CreateCompanyRequest;
import com.mtemnohud.dbuilder.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "company")
    @JsonIgnore
    private List<UserEntity> users = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "company")
    @JsonIgnore
    private List<Building> buildings = new ArrayList<>();

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    public static Company createFromRequest(CreateCompanyRequest request) {
        Company object = new Company();
        object.setName(request.getName());
        object.setDescription(request.getDescription());
        return object;
    }

}
