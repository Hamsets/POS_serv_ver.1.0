package com.pos.Service;

import com.pos.Data.Entities.User;
import com.pos.Service.Dto.UserDto;

public class Mapper {
    public static UserDto userToUserDto (User user){

        UserDto userDto = new UserDto(user.getId(),user.getFirstName(), user.getLastName(), user.getSurName(),
                user.getEmail(), user.getPassword(), user.getRole(), user.getRating(), user.getDeleted());
          return userDto;
    }

    public static User UserDtoToUser (UserDto userDto){
        User user = new User(userDto.getId(),userDto.getFirstName(), userDto.getLastName(), userDto.getSurName(),
                userDto.getEmail(), userDto.getPassword(), userDto.getRole(), userDto.getRating(), userDto.getDeleted());
        return user;
    }
}
