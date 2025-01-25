package com.mhy.cescsap.service.impl;

import com.mhy.cescsap.mapper.AdminMapper;
import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
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
    public User login(User user) {
        //Long teacherId = teacher.getTeacherId();
        //根据身份标识判断用户类型
        if(user.getRole()==0){
            //User转Admin
            Admin admin = new Admin();
            admin.setName(user.getName());
            admin.setPassword(user.getPassword());
            //查询admin
            String name = admin.getName();
            //根据adminId查询admin
            Admin admin1 = adminMapper.selectAdminByName(name);
            log.info("Admin is {}",admin1);
            if (admin1!=null && admin1.getPassword().equals(admin.getPassword())) {
                admin1.setId(admin1.getAdminId());
                log.info("User is {}",admin1);
                return admin1;
            } else {
                return null;
            }
        }else if(user.getRole()==1){
            //User转Teacher
            Teacher teacher = new Teacher();
            teacher.setName(user.getName());
            teacher.setPassword(user.getPassword());
            //查询教师
            //Long teacherId = teacher.getTeacherId();
            String name = teacher.getName();
            Teacher teacher1 = teacherMapper.selectTeacherByName(name);

            log.info("Teacher is {}",teacher1);
            if (teacher1!=null && teacher1.getPassword().equals(teacher.getPassword())) {
                teacher1.setId(teacher1.getTeacherId());
                log.info("User is {}",teacher1);
                return teacher1;
            } else {
                return null;
            }
        }else if(user.getRole()==2){
            //User转学生
            //Long studentId = student.getStudentId();
            Student student = new Student();
            student.setName(user.getName());
            student.setPassword(user.getPassword());
            //查询学生
            //Long studentId = student.getStudentId();
            String name = student.getName();
            Student student1 = studentMapper.selectStudentByName(name);

            log.info("Student is {}",student1);
            if (student1!=null && student1.getPassword().equals(student.getPassword())) {
                student1.setId(student1.getStudentId());
                log.info("User is {}",student1);
                return student1;
            } else {
                return null;
            }
        }else {
            return null;
        }
        //return null;
    }
}
