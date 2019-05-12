package com.mtemnohud.dbuilder.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mtemnohud.dbuilder.model.entity.BuildingTask;
import com.mtemnohud.dbuilder.model.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "is_company_owner")
    private Boolean isCompanyOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private Company company;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "user")
    @JsonIgnore
    private List<BuildingTask> buildingTasks = new ArrayList<>();

    public static class Builder {

        private String username;

        private String password;

        private String name;

        private String surname;

        private Boolean isCompanyOwner;

        public Builder() {
            this.isCompanyOwner = false;
        }

        public Builder addCredentials(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public Builder addInfo(String name, String surname) {
            this.name = name;
            this.surname = surname;
            return this;
        }

        public Builder addCompanyOwnerStatus(Boolean isCompanyOwner) {
            this.isCompanyOwner = isCompanyOwner;
            return this;
        }

        public UserEntity build() {
            UserEntity user = new UserEntity();

            user.setEnabled(true);
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setIsCompanyOwner(isCompanyOwner);

            return user;
        }

    }

}
