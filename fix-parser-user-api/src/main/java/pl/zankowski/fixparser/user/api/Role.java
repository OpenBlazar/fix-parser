package pl.zankowski.fixparser.user.api;

import java.util.Objects;

public class Role {

    public static final Role USER_ROLE = new Role("User");
    public static final Role ADMIN_ROLE = new Role("Admin");

    private final String name;

    public Role(final String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }

}
