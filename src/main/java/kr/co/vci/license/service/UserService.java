package kr.co.vci.license.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.vci.license.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    UserMapper dao;

    public String testSelect() {
        return dao.testSelect();
    }
}
