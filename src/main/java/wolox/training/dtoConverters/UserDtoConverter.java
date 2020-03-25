package wolox.training.dtoConverters;

import wolox.training.dto.UserDTO;
import wolox.training.models.User;

public class UserDtoConverter {
    public static User convert(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBooks(userDTO.getBooks());

        return user;
    }

    public static User convertExisting(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBooks(userDTO.getBooks());

        return user;
    }
}
