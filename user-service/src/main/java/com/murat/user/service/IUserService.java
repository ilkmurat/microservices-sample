package com.murat.user.service;

import com.murat.user.VO.ResponseTemplateVO;
import com.murat.user.entity.User;

public interface IUserService {

    public User saveUser(User user) ;
    public ResponseTemplateVO getUserWithDepartment(Long userId);
}
