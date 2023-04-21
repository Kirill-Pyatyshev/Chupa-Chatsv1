package com.chupachats.services;

import com.chupachats.dto.AuthUserDto;
import com.chupachats.dto.AuthUserResponceDto;
import com.chupachats.dto.UserDto;
import com.chupachats.exception.UserNullFoundException;
import com.chupachats.models.Image;
import com.chupachats.models.User;
import com.chupachats.models.enums.Role;
import com.chupachats.repositories.ImageRepository;
import com.chupachats.repositories.UserRepository;
import com.chupachats.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public AuthUserResponceDto createUser(User user) throws AuthenticationException, UserNullFoundException {
        String email = user.getEmail();

        if (userRepository.findByEmail(email) != null) {
            throw new UserNullFoundException("Пользователь с email'ом:" + email + " уже существует!");
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);

        User userSaved = userRepository.save(user);

        if(userSaved==null) {
            throw new UserNullFoundException("Пользователь с email'ом:" + email + " не найден!");
        }
        String token = jwtTokenProvider.createToken(user);

        AuthUserResponceDto userResponce = new AuthUserResponceDto(email, token);

        return userResponce;
    }

    public AuthUserResponceDto login(AuthUserDto authUserDto) throws AuthenticationException, UserNullFoundException {
        String email = authUserDto.getEmail();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authUserDto.getPassword()));

        User user = userRepository.findByEmail(email);

        if(user==null) {
            throw new UserNullFoundException("Пользователь с email'ом:" + email + " не найден!");
        }
        String token = jwtTokenProvider.createToken(user);

        AuthUserResponceDto userResponce = new AuthUserResponceDto(email, token);

        return userResponce;
    }

    public List<UserDto> all() throws UserNullFoundException {
        if (listUsers().size() == 0) {
            throw new UserNullFoundException("Пользователи не найдены!");
        }
        return usersListToDto(listUsers());
    }

    public UserDto findUserById(Long id) throws UserNullFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ID:" + id + " не найден!"));
        return userToDto(user);
    }

    public void deleteUserById(Long id) throws UserNullFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ID:" + id + " не найден!"));
        userRepository.deleteById(id);
    }

    public void banUserById(Long id) throws UserNullFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ID:" + id + " не найден!"));
        if (user.isActive()) {
            user.setActive(false);
        } else {
            user.setActive(true);
        }
        userRepository.save(user);
    }

    public void addImage(MultipartFile file, Long id) throws IOException, UserNullFoundException {
        Image image;
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ID:" + id + " не найден!"));
        if (file.getSize() != 0) {
            image = toImageEntity(file);
            user.addImageToUser(image);
        }
        userRepository.save(user);
    }

    public Long getImageId(Long id) throws UserNullFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с ID:" + id + " не найден!"));
        return user.getImages().get(0).getId();
    }

    private Image toImageEntity(MultipartFile file1) throws IOException {
        Image image = new Image();
        image.setName(file1.getName());
        image.setOriginalFileName(file1.getOriginalFilename());
        image.setContentType(file1.getContentType());
        image.setSize(file1.getSize());
        image.setBytes(file1.getBytes());
        return imageRepository.save(image);
    }


    private UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getFirstname() + " " + user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .age(user.getAge())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .build();
    }

    private AuthUserDto userToAuthUserDto(User user) {
        return AuthUserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    private List<UserDto> usersListToDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(userToDto(user));
        }
        return userDtoList;
    }
}
