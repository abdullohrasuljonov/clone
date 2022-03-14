package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.codingbat.entity.Language;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotNull(message = "Name should be not empty!")
    private String name;

    @NotNull(message = "Description should be not empty!")
    private String description;

    @NotNull(message = "Language id should be not empty!")
    private List<Language> languages;
}
