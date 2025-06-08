package tcatelie.microservice.auth.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    STATIC_USER("STATIC_USER"),
    USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
