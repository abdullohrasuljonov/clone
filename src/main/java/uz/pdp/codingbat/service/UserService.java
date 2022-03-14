package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.ApiResponse;
import uz.pdp.codingbat.payload.UserDto;
import uz.pdp.codingbat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> all() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public ApiResponse addUser(UserDto userDto) {
        boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists)
            return new ApiResponse("Email already exist!", false);
        boolean existsByPassword = userRepository.existsByPassword(userDto.getPassword());
        if (existsByPassword)
            return new ApiResponse("Password already exist!", false);
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("Successfully added!", true);
    }

    public ApiResponse editUser(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResponse("User not found!", false);
        boolean exists = userRepository.existsByEmail(userDto.getEmail());
        if (exists)
            return new ApiResponse("Email already exist!", false);
        boolean existsByPassword = userRepository.existsByPassword(userDto.getPassword());
        if (existsByPassword)
            return new ApiResponse("Password already exist!", false);
        User user = optionalUser.get();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse("Successfully edited!", true);
    }

    public ApiResponse deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse("Successfully deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Deleting error!", false);
        }
    }
}





