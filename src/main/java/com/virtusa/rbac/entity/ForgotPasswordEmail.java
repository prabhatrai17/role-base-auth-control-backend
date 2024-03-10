package com.virtusa.rbac.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ForgotPasswordEmail {

    @Id
    @UuidGenerator
    private String Id;
    private String recipient;
    private String subject;
    private String body;


}
