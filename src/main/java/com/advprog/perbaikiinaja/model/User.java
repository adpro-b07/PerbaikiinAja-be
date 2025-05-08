package com.advprog.perbaikiinaja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User implements Serializable {
    @Id
    protected String id;
    protected String namaLengkap;
    protected String email;
    protected String password;
    protected String noTelp;
    protected String role;
    
    protected User(String id, String namaLengkap, String email, String password, String noTelp, String role) {
        this.id = id;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.password = password;
        this.noTelp = noTelp;
        this.role = role;
    }

    public static User createUser(String type, String id, String namaLengkap, String email, 
                                String password, String noTelp, String alamat) {
        if ("Admin".equalsIgnoreCase(type)) {
            return new Admin(id, namaLengkap, email, password, noTelp);
        } else if ("Pengguna".equalsIgnoreCase(type)) {
            return new Pengguna(id, namaLengkap, email, password, noTelp, alamat);
        } else if ("Teknisi".equalsIgnoreCase(type)) {
            return new Teknisi(id, namaLengkap, email, password, noTelp, alamat);
        }
        throw new IllegalArgumentException("Unknown user type: " + type);
    }
}