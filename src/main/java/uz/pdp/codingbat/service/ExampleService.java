package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Category;
import uz.pdp.codingbat.entity.Example;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.ExampleDto;
import uz.pdp.codingbat.repository.ExampleRepository;
import uz.pdp.codingbat.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleService {

    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;

    public List<Example> all(){
        return exampleRepository.findAll();
    }

    public Example getById(Integer id){
        Optional<Example> optionalExample = exampleRepository.findById(id);
        return optionalExample.orElse(null);
    }

    public List<Example> byTaskId(Integer taskId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent())
            return null;
        return exampleRepository.findAllByTaskId(taskId);
    }

    public ApiResponse addExample(ExampleDto exampleDto){
        boolean exists = exampleRepository.existsByTextAndTaskId(exampleDto.getText(), exampleDto.getTaskId());
        if (exists)
            return new ApiResponse("This example already exist such an task id!",false);
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found!",false);
        Example example=new Example();
        example.setText(exampleDto.getText());
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("Successfully added!",true);
    }

    public ApiResponse editExample(Integer id,ExampleDto exampleDto){
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent())
            return new ApiResponse("Example not found!",false);
        boolean exists = exampleRepository.existsByTextAndTaskId(exampleDto.getText(), exampleDto.getTaskId());
        if (exists)
            return new ApiResponse("This example already exist such an task id!",false);
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found!",false);
        Example example=optionalExample.get();
        example.setText(exampleDto.getText());
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResponse("Successfully edited!",true);
    }

    public ApiResponse deleteExample(Integer id){
        try {
            exampleRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }
}
