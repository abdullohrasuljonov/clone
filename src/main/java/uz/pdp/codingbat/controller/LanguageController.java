package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.LanguageDto;
import uz.pdp.codingbat.service.LanguageService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    LanguageService languageService;

    @PostMapping
    public ResponseEntity<ApiResponse> addLanguage(@Valid @RequestBody LanguageDto languageDto) {
        ApiResponse apiResponse = languageService.addLanguage(languageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<Language>> all() {
        List<Language> all = languageService.all();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getById(@PathVariable Integer id) {
        Language language = languageService.getById(id);
        return ResponseEntity.ok(language);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editLanguage(@PathVariable Integer id, @Valid @RequestBody LanguageDto languageDto) {
        ApiResponse apiResponse = languageService.editLanguage(id, languageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLanguage(@PathVariable Integer id) {
        ApiResponse apiResponse = languageService.deleteLanguage(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
