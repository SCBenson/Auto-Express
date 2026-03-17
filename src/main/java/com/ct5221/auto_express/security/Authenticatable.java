package com.ct5221.auto_express.security;

public interface Authenticatable {
    Long getId();
    String getEmail();
    String getPassword();
    String getRole();
}
