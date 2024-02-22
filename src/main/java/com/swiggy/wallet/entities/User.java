package com.swiggy.wallet.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private Country country;

    public User(String userName, String password, Country country) {
        this.userName = userName;
        this.password = password;
        this.wallet = new Wallet(country);
        this.country = country;
    }
}
