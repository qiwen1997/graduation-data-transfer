package com.einvoice.dao;

import com.einvoice.entity.UserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

  UserEntity login(UserEntity user);

  UserEntity search(UserEntity user);

  Integer addUser(UserEntity user);

  List<UserEntity> findAllUser();
}
