package wolox.training.dtoConverters;

import wolox.training.dto.UserDTO;
import wolox.training.models.User;

public class UserDtoConverter {
    private UserDtoConverter() {}

    public static User convertDtoToUser(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBooks(userDTO.getBooks());

        return user;
    }

    public static User convertDtoToExistingUser(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBooks(userDTO.getBooks());

        return user;
    }

    public static UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getName());
        userDTO.setName(user.getName());
        userDTO.setBirthdate(user.getBirthdate());
        userDTO.setBooks(user.getBooks());

        return userDTO;
    }
}
