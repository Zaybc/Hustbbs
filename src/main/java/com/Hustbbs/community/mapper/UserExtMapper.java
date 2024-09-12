package com.Hustbbs.community.mapper;

import com.Hustbbs.community.dto.UserDTO;
import com.Hustbbs.community.model.User;

import java.util.List;

public interface UserExtMapper {

    List<User> selectBySearch(UserDTO userDTO);
}