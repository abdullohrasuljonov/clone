package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Example;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.ExampleDto;
import uz.pdp.codingbat.service.ExampleService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @Autowired
    ExampleService exampleService;

    @PostMapping
    public ResponseEntity<ApiResponse> addExample(@Valid @RequestBody ExampleDto exampleDto) {
        ApiResponse apiResponse = exampleService.addExample(exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<Example>> all() {
        List<Example> all = exampleService.all();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/byTaskId/{taskId}")
    public ResponseEntity<List<Example>> byTaskId(@PathVariable Integer taskId) {
        List<Example> examples = exampleService.byTaskId(taskId);
        return ResponseEntity.ok(examples);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Example> getById(@PathVariable Integer id) {
        Example example = exampleService.getById(id);
        return ResponseEntity.ok(example);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editExample(@PathVariable Integer id, @Valid @RequestBody ExampleDto exampleDto) {
        ApiResponse apiResponse = exampleService.editExample(id, exampleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteExample(@PathVariable Integer id) {
        ApiResponse apiResponse = exampleService.deleteExample(id);
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
