package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Answer;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.AnswerDto;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.repository.AnswerRepository;
import uz.pdp.codingbat.repository.TaskRepository;
import uz.pdp.codingbat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    public List<Answer> all() {
        return answerRepository.findAll();
    }

    public Answer getById(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.orElse(null);
    }

    public List<Answer> byUserId(Integer userId) {
        return answerRepository.findAllByUserId(userId);
    }

    public ApiResponse addAnswer(AnswerDto answerDto) {
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found!", false);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found!", false);
        Answer answer = new Answer();
        answer.setCorrect(answerDto.isCorrect());
        answer.setText(answerDto.getText());
        answer.setTask(optionalTask.get());
        answer.setUser(optionalUser.get());
        answerRepository.save(answer);
        return new ApiResponse("Successfully added!", true);
    }

    public ApiResponse editAnswer(Integer id, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (!optionalAnswer.isPresent())
            return new ApiResponse("Answer not found!", false);
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found!", false);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found!", false);
        Answer answer = optionalAnswer.get();
        answer.setCorrect(answerDto.isCorrect());
        answer.setText(answerDto.getText());
        answer.setTask(optionalTask.get());
        answer.setUser(optionalUser.get());
        answerRepository.save(answer);
        return new ApiResponse("Successfully edited!", true);
    }

    public ApiResponse deleteAnswer(Integer id) {
        try {
            answerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Deleting error!", false);
        }
    }
}