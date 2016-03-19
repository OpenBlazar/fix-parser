package net.openblazar.bfp.data.fix;

/**
 * @author Wojciech Zankowski
 */
public class FixValue {

    private final String value;
    private final String description;

    public FixValue(String value) {
        this(value, "");
    }

    public FixValue(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixValue fixValue = (FixValue) o;

        if (getValue() != null ? !getValue().equals(fixValue.getValue()) : fixValue.getValue() != null) return false;
        return getDescription() != null ? getDescription().equals(fixValue.getDescription()) : fixValue.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixValue{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
