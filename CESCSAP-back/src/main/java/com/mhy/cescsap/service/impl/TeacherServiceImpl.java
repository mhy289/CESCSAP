package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.*;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mhy.cescsap.utils.AccountGeneratorUtils.generateAccount;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    TeacherEvaluationStatsMapper teacherEvaluationStatsMapper;

    @Autowired
    SCMapper scMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    EvaluationMapper evaluationMapper;

    @Autowired
    EvaluationDetailMapper evaluationDetailMapper;

    @Override
    public List<Teacher> getTeachers() {
        return teacherMapper.selectAllTeachers();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherMapper.selectTeacherById(id);
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        log.debug("Teacher is {}", teacher);
        long account;
        do {
            account =generateAccount();
        } while (teacherMapper.existsByAccount(account)); // 查重
        teacher.setAccount(account);
        teacher.setPassword("123456");
        return teacherMapper.insertTeacher(teacher);
    }

    @Override
    public Integer deleteTeacher(Long id) {
        return teacherMapper.deleteTeacher(id);
    }

    @Override
    public Integer updateTeacher(Teacher teacher) {
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public Teacher login(Teacher teacher) {
        //Long teacherId = teacher.getTeacherId();
        String name = teacher.getName();
        Teacher teacher1 = teacherMapper.selectTeacherByName(name);
        log.info("Teacher is {}",teacher1);
        if (teacher1!=null && teacher1.getPassword().equals(teacher.getPassword())) {
            log.info("User is {}",teacher1);
            return teacher1;
        } else {
            return null;
        }
    }

    @Override
    public Teacher getTeacherEvaluate(Long id) {
        List<TeacherEvaluationStats> teacherEvaluationStatsByTeacherId = teacherEvaluationStatsMapper.getTeacherEvaluationStatsByTeacherId(id);
        Teacher teacher = teacherMapper.selectTeacherById(id);
        teacher.setTeacherEvaluationStats(teacherEvaluationStatsByTeacherId);
        return teacher;
    }

    @Override
    public List<Teacher> getTeachersByStudent(Student student) {
        // 根据 studentId 查询该学生选修的课程（StudentCourse 关联记录）
        if(student.getStudentId() == null){
            throw new BusinessException(ExceptionType.STU_ERR,"学生错误");
        }
        List<StudentCourse> studentCourses = scMapper.selectByCondition2(student.getStudentId());
        List<Teacher> teachers = new ArrayList<Teacher>();
        // 遍历每条关联记录，根据 courseId 查询课程，再由课程获取教师信息
        if(studentCourses !=null && studentCourses.isEmpty()){
            for(StudentCourse sc : studentCourses){
                Long courseId = sc.getCourseId();
                Course course = courseMapper.getCourseById(courseId);
                if(course != null){
                    Teacher teacher = teacherMapper.selectTeacherById(course.getTeacherId());
                    if(teacher != null){
                        teachers.add(teacher);
                    }else{
                        throw new BusinessException(ExceptionType.TEACHER_ERR,"教师查询失败");
                    }
                }else{
                    throw new BusinessException(ExceptionType.COURSE_ERR,"课程查询失败");
                }
            }
        }
        return teachers;
    }

    @Override
    public Integer saveEvaluations(TeacherEvaluationsDTO teacherEvaluationsDTO) {
        List<Evaluation> evaluationList = teacherEvaluationsDTO.getEvaluationList();
        if(evaluationList== null || evaluationList.isEmpty()){
            throw new BusinessException(ExceptionType.EVALUATE_ERR,"评价为空");
        }
        for(Evaluation evaluation : evaluationList){
            // 保存评价主记录
            Integer i1 = evaluationMapper.insertEvaluation(evaluation);
            if(i1 == null || i1<=0){
                throw new BusinessException(ExceptionType.EVALUATE_ERR,"评价主记录保存失败");
            }
            // 保存对应的评价明细，设置外键 evaluationId
            if(evaluation.getEvaluationDetails()!=null){
                for(EvaluationDetail evaluationDetail : evaluation.getEvaluationDetails()){
                    evaluationDetail.setEvaluationId(evaluation.getEvaluationId());
                    Integer i = evaluationDetailMapper.insertEvaluationDetail(evaluationDetail);
                    if(i == null || i<=0){
                        throw new BusinessException(ExceptionType.EVALUATE_ERR,"评价明细保存失败");
                    }
                }
            }
        }
        return 1;
    }

    @Override
    public PageItem<Teacher> queryPage(Integer current, Integer size) {
        PageHelper.startPage(current, size);
        List<Teacher> teachers = teacherMapper.selectAllTeachers();
        Page<Teacher> pages = (Page<Teacher>) teachers;
        return new PageItem<>(pages.getTotal(), teachers);
    }
}
