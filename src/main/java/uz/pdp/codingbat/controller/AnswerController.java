package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Answer;
import uz.pdp.codingbat.payload.AnswerDto;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.service.AnswerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping
    public ResponseEntity<ApiResponse> addAnswer(@Valid @RequestBody AnswerDto answerDto) {
        ApiResponse apiResponse = answerService.addAnswer(answerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<List<Answer>> all() {
        List<Answer> all = answerService.all();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<List<Answer>> byUserId(@PathVariable Integer userId) {
        List<Answer> answers = answerService.byUserId(userId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getById(@PathVariable Integer id) {
        Answer answer = answerService.getById(id);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editAnswer(@PathVariable Integer id, @Valid @RequestBody AnswerDto answerDto) {
        ApiResponse apiResponse = answerService.editAnswer(id, answerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAnswer(@PathVariable Integer id) {
        ApiResponse apiResponse = answerService.deleteAnswer(id);
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
