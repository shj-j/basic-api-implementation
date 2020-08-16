package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    public UserController(UserRepository userRepository, RsEventRepository rsEventRepository){
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @PostMapping("/users")
    public void register(@RequestBody @Valid User user){
        UserEntity entity = UserEntity.builder()
                .userName(user.getUserName())
                .gender(user.getGender())
                .age(user.getAge())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
        userRepository.save(entity);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUserById(@PathVariable int userId){

        userRepository.deleteById(userId);
        rsEventRepository.deleteByUserId(userId);

    }

    @GetMapping("users/{userId}")
    public Optional<UserEntity> getUserByUserId(@PathVariable int userId) {
        return userRepository.findById(userId);
    }
}
