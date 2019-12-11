package ru.itpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Text {
    private String id;
    private String name;
    private String textURI;

    public Text(String name, String textURI) {

        this.name = name;
        this.textURI = textURI;
    }
}
