package fr.istic.m2.mcq_api.parser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Meta {
    private String type;
    private String value;

    public Meta() {
    }

    public Meta(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}