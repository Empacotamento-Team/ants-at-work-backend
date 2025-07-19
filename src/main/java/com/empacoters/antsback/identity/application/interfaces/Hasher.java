package com.empacoters.antsback.identity.application.interfaces;

public interface Hasher {
    String hash(String password);
    String hashToken(String token);
    boolean matches(String password, String hash);
}
