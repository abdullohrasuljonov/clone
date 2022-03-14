package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleDto {

    @NotNull(message = "Text should be not empty!")
    private String text;

    @NotNull(message = "Task id should be not empty!")
    private Integer taskId;
}
