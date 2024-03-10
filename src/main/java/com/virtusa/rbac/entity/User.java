package com.virtusa.rbac.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    private String password;
    // private String roles;
    @ManyToOne
    @JoinColumn(name="profile_id")
     private Profile profile;
    // private List<ApplicationDetails> applicationDetailsList;

}
