package fr.istic.m2.mcq_api.parser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Option {
    private String type;
    private String text;

    public Option() {
    }

    public Option(String type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Option{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}