package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.AdminMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.Admin;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.Teacher;
import com.mhy.cescsap.pojo.User;
import com.mhy.cescsap.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    AdminMapper adminMapper;

    @Override
    public User login(User user,String loginType) {
        Long role = user.getRole();
        String password = user.getPassword();

        // 根据角色选择登录字段和查询方式
        return switch (role.intValue()) {
            case 0 -> // 管理员
                    handleAdminLogin(user, loginType, password);
            case 1 -> // 教师
                    handleTeacherLogin(user, loginType, password);
            case 2 -> // 学生
                    handleStudentLogin(user, loginType, password);
            default -> {
                log.warn("登录失败，角色: {}", role);
                yield null;
            }
        };
    }

    private User loginAdminByAccount(User user, String password) {
        Long account = user.getAccount();
        Admin admin = adminMapper.selectAdminByAccount(account);
        if (admin != null && admin.getPassword().equals(password)) {
            admin.setId(admin.getAdminId());
            log.info("管理员账户登录成功: {}", admin);
            return admin;
        }
        log.warn("管理员账户登录失败，账户: {}", account);
        return null;
    }

    private User loginAdminByName(User user, String password) {
        String name = user.getName();
        Admin admin = adminMapper.selectAdminByName(name);
        if (admin != null && admin.getPassword().equals(password)) {
            admin.setId(admin.getAdminId());
            log.info("管理员用户名登录成功: {}", admin);
            return admin;
        }
        log.warn("管理员用户名登录失败，用户名: {}", name);
        return null;
    }

    private User handleAdminLogin(User user, String loginType, String password) {
        return switch (loginType) {
            case "account" -> loginAdminByAccount(user, password);
            case "name" -> loginAdminByName(user, password);
            default -> {
                log.warn("管理员不支持该登录类型: {}", loginType);
                yield null;
            }
        };
    }

    private User handleTeacherLogin(User user, String loginType, String password) {
        return switch (loginType) {
            case "account" -> loginTeacherByAccount(user, password);
            case "number" -> loginTeacherByNumber(user, password);
            case "name" -> loginTeacherByName(user, password);
            default -> {
                log.warn("教师不支持该登录类型: {}", loginType);
                yield null;
            }
        };
    }

    private User loginTeacherByAccount(User user, String password) {
        Long account = user.getAccount();
        Teacher teacher = teacherMapper.selectTeacherByAccount(account);
        if (teacher != null && teacher.getPassword().equals(password)) {
            teacher.setId(teacher.getTeacherId());
            log.info("教师账户登录成功: {}", teacher);
            return teacher;
        }
        log.warn("教师账户登录失败，账户: {}", account);
        return null;
    }

    private User loginTeacherByNumber(User user, String password) {
        String number = user.getNumber();
        Teacher teacher = teacherMapper.selectTeacherByNumber(number);
        if (teacher != null && teacher.getPassword().equals(password)) {
            teacher.setId(teacher.getTeacherId());
            log.info("教师工号登录成功: {}", teacher);
            return teacher;
        }
        log.warn("教师工号登录失败，工号: {}", number);
        return null;
    }

    private User loginTeacherByName(User user, String password) {
        String name = user.getName();
        Teacher teacher = teacherMapper.selectTeacherByName(name);
        if (teacher != null && teacher.getPassword().equals(password)) {
            teacher.setId(teacher.getTeacherId());
            log.info("教师用户名登录成功: {}", teacher);
            return teacher;
        }
        log.warn("教师用户名登录失败，用户名: {}", name);
        return null;
    }

    private User handleStudentLogin(User user, String loginType, String password) {
        return switch (loginType) {
            case "number" -> loginStudentByNumber(user, password);
            case "name" -> loginStudentByName(user, password);
            default -> {
                log.warn("学生不支持该登录类型: {}", loginType);
                yield null;
            }
        };
    }

    private User loginStudentByNumber(User user, String password) {
        String number = user.getNumber();
        Student student = studentMapper.selectStudentByNumber(number);
        if (student != null && student.getPassword().equals(password)) {
            student.setId(student.getStudentId());
            log.info("学生学号登录成功: {}", student);
            return student;
        }
        log.warn("学生学号登录失败，学号: {}", number);
        log.warn("学生学号登录失败: {}", student);
        return null;
    }

    private User loginStudentByName(User user, String password) {
        String name = user.getName();
        Student student = studentMapper.selectStudentByName(name);
        if (student != null && student.getPassword().equals(password)) {
            student.setId(student.getStudentId());
            log.info("学生用户名登录成功: {}", student);
            return student;
        }
        log.warn("学生用户名登录失败，用户名: {}", name);
        log.warn("学生学号登录失败: {}", student);
        return null;
    }

    @Override
    public Integer checkAdmin(Integer integer) {
        return null;
    }

    @Override
    public User getUser(User user) {
        //管理员
        if(user.getRole()==0){
            Admin admin = adminMapper.selectAdminByName(user.getName());
            if(admin==null){
                throw new BusinessException(ExceptionType.USER_NOT_FOUND,"不存在");
            }
            return admin;
        } else if (user.getRole()==1) {
            Teacher teacher = teacherMapper.selectTeacherByName(user.getName());
            if(teacher==null){
                throw new BusinessException(ExceptionType.USER_NOT_FOUND,"不存在");
            }
            return teacher;
        } else if (user.getRole()==2) {
            Student student = studentMapper.selectStudentByName(user.getName());
            if(student==null){
                throw new BusinessException(ExceptionType.USER_NOT_FOUND,"不存在");
            }
            return student;
        }else {
            throw new BusinessException(ExceptionType.USER_ERR,"不存在");
        }
    }
}
