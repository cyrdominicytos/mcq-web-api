package fr.istic.m2.mcq_api.parser;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Data
public class ParsingQuestion {
    private String title;
    private List<Meta> metaData;
    private List<Option> options;

    public ParsingQuestion() {
        this.metaData = new ArrayList<>();
        this.options = new ArrayList<>();
    }


    public void addMeta(Meta meta) {
        this.metaData.add(meta);
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", metaData=" + metaData +
                ", options=" + options +
                '}';
    }
}
