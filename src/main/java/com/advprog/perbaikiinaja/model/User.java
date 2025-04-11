package com.advprog.perbaikiinaja.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {
    protected String id;
    protected String namaLengkap;
    protected String email;
    protected String password;
    protected String noTelp;
    protected String role;
    
    // Protected constructor to prevent direct instantiation
    protected User(String id, String namaLengkap, String email, String password, String noTelp, String role) {
        this.id = id;
        this.namaLengkap = namaLengkap;
        this.email = email;
        this.password = password;
        this.noTelp = noTelp;
        this.role = role;
    }
    
    // Factory method
    public static User createUser(String type, String id, String namaLengkap, String email, 
                                 String password, String noTelp) {
        if ("ADMIN".equalsIgnoreCase(type)) {
            return new Admin(id, namaLengkap, email, password, noTelp);
        } else if ("PENGGUNA".equalsIgnoreCase(type)) {
            return new Pengguna(id, namaLengkap, email, password, noTelp);
        } else if ("TEKNISI".equalsIgnoreCase(type)) {
            return new Teknisi(id, namaLengkap, email, password, noTelp);
        }
        throw new IllegalArgumentException("Unknown user type: " + type);
    }
}