package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotNull(message = "Name should be not empty!")
    private String name;

    @NotNull(message = "Text should be not empty!")
    private String text;

    @NotNull(message = "Solution should be not empty!")
    private String solution;

    @NotNull(message = "Method should be not empty!")
    private String method;

    private boolean hasStar;

    @NotNull(message = "Language id should be not empty!")
    private Integer languageId;
}
