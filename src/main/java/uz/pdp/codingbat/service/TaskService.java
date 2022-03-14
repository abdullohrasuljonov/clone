package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.TaskDto;
import uz.pdp.codingbat.repository.LanguageRepository;
import uz.pdp.codingbat.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LanguageRepository languageRepository;

    public List<Task> all(){
        return taskRepository.findAll();
    }

    public List<Task> byLanguageId(Integer languageId){
        return taskRepository.findAllByLanguageId(languageId);
    }

    public Task getById(Integer id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.orElse(null);
    }

    public ApiResponse addTask(TaskDto taskDto){
        boolean exists = taskRepository.existsByNameAndLanguageId(taskDto.getName(), taskDto.getLanguageId());
        if (exists)
            return new ApiResponse("Name already exist such an language id!",false);
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found!",false);
        Task task=new Task();
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setSolution(taskDto.getSolution());
        task.setHasStar(taskDto.isHasStar());
        task.setLanguage(optionalLanguage.get());
        taskRepository.save(task);
        return new ApiResponse("Successfully added!",true);
    }

    public ApiResponse editTask(Integer id,TaskDto taskDto){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
            return new ApiResponse("Task not found!",false);
        boolean exists = taskRepository.existsByNameAndLanguageId(taskDto.getName(), taskDto.getLanguageId());
        if (exists)
            return new ApiResponse("Name already exist such an language id!",false);
        Optional<Language> optionalLanguage = languageRepository.findById(taskDto.getLanguageId());
        if (!optionalLanguage.isPresent())
            return new ApiResponse("Language not found!",false);
        Task task=optionalTask.get();
        task.setName(taskDto.getName());
        task.setText(taskDto.getText());
        task.setMethod(taskDto.getMethod());
        task.setSolution(taskDto.getSolution());
        task.setHasStar(taskDto.isHasStar());
        task.setLanguage(optionalLanguage.get());
        taskRepository.save(task);
        return new ApiResponse("Successfully edited!",true);
    }

    public ApiResponse deleteTask(Integer id){
        try {
            taskRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!",true);
        }catch (Exception e){
            return new ApiResponse("Deleting error!",false);
        }
    }
}
