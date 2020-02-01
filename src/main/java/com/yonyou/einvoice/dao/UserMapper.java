package com.yonyou.einvoice.dao;

import com.yonyou.einvoice.entity.UserEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

  UserEntity login(UserEntity user);

  boolean addUser(UserEntity user);

  List<UserEntity> findAllUser();
}
