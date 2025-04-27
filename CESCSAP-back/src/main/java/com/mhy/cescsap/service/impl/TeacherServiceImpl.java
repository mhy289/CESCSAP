package com.mhy.cescsap.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhy.cescsap.mapper.*;
import com.mhy.cescsap.myexception.BusinessException;
import com.mhy.cescsap.myexception.ExceptionType;
import com.mhy.cescsap.pojo.*;
import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mhy.cescsap.utils.AcademicTermUtils.classifyAcademicTerm;
import static com.mhy.cescsap.utils.AccountGeneratorUtils.generateAccount;
import static com.mhy.cescsap.utils.SchoolNumberGeneratorUtils.generateTeacherNumber;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    //设置分数权重作为常量,
    public static final Double USUAL_SCORE_RATE = 0.5;
    public static final Double EXAM_SCORE_RATE = 0.5;

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

    @Autowired
    ClassMapper classMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    WarningMapper warningMapper;
    @Autowired
    DepartmentMapper departmentMapper;

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
        String department = teacher.getDepartment();
        Department d = departmentMapper.getDepartmentByName(department);
        if(d == null || d.getDepartmentId()== null){
            throw new BusinessException(ExceptionType.DEP_ERR,"部门错误");
        }
        teacher.setDepartmentId(d.getDepartmentId());
        long account;
        do {
            account =generateAccount();
        } while (teacherMapper.existsByAccount(account)); // 查重
        teacher.setAccount(account);
        teacher.setPassword("123456");
        teacher.setLoginStatus(0);
        teacher.setRole(1L);
        teacher.setBirthDate(new Date(2000000000));
        teacher.setHireDate(new Date());
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

    @Override
    public List<Student> getStudentsByTeacherId(Long teacherId) {
        return classMapper.selectStudentsByTeacher(teacherId);
    }

    @Override
    public List<Class> getClassesByTeacherId(Long teacherId) {
        return classMapper.selectClassesByTeacher(teacherId);
    }

    @Override
    public List<Student> getStudentsByClass(Long teacherId, Long classId) {
        List<StudentCourse> studentCourses = scMapper.selectStudentsByClass(teacherId, classId);
        if(studentCourses == null || studentCourses.isEmpty()){
            throw new BusinessException(ExceptionType.STU_ERR,"学生错误");
        }
        List<Student> students = new ArrayList<Student>();
        for(StudentCourse sc : studentCourses){
            Student student = studentMapper.getStudentById(sc.getStudentId());
            //查重，防止重复
            if(students.contains(student)){
                continue;
            }
            students.add(student);
        }
        return students;
    }

    @Override
    public List<StudentCourse> getStudentsByCourse(Long teacherId, Long courseId) {
        return scMapper.selectStudentsByCourse(teacherId, courseId);
    }

    @Override
    public Integer saveScores(StudentCourse sc) {
        // 计算总分和绩点
        // 计算总分（按比例）
        double usual = sc.getUsualScore() == null ? 0 : sc.getUsualScore();
        double exam  = sc.getExamScore()  == null ? 0 : sc.getExamScore();
        double usualRate = sc.getUsualRate() == null ? USUAL_SCORE_RATE : sc.getUsualRate();
        double examRate  = sc.getExamRate()  == null ? EXAM_SCORE_RATE : sc.getExamRate();
        double total = usual * usualRate + exam * examRate;
        //全部保存
        sc.setUsualScore(usual);
        sc.setExamScore(exam);
        sc.setUsualRate(usualRate);
        sc.setExamRate(examRate);
        sc.setScore(total);
        if(total<60){
            Warning warning = new Warning();
            warning.setTeacherId(sc.getTeacherId());
            warning.setStudentId(sc.getStudentId());
            warning.setCourseId(sc.getCourseId());
            //低于60分,新建预警信息
            Integer i = warningMapper.insertWarning(warning);
        }

        // 计算 5 分制绩点（线性映射）
        sc.setGpa(calcGpa5(total));

        // 设置考试日期
        sc.setExamDate(new Date());
        //通过工具类获取学期
        sc.setTerm(classifyAcademicTerm(sc.getExamDate().toString()));
        sc.setEvaluateStatus(0);
        Long studentId = sc.getStudentId();
        Integer i = updateStudentOverallGPA(studentId,sc.getGpa());
        if(i== null|| i<=0){
            throw new BusinessException(ExceptionType.STU_ERR,"学生绩点更新失败");
        }
        return scMapper.updateScores(sc);
    }


     //更新学生总绩点
    private Integer updateStudentOverallGPA(Long studentId,Double gpa) {
        // 获取所有有效课程绩点
        List<Double> validGPAs = scMapper.selectValidGPAs(studentId);

        if (validGPAs.isEmpty()) {
            return studentMapper.updateGPA(studentId, gpa);
        }

        // 计算加权平均（假设所有课程学分相同）
        double total = validGPAs.stream().mapToDouble(Double::doubleValue).sum();
        double avgGPA = total / validGPAs.size();

        return studentMapper.updateGPA(studentId, avgGPA);
    }

    @Override
    public List<Course> getCoursesByTeacherId(Long id) {
        return courseMapper.getCoursesByTeacherId(id);
    }

    @Override
    public Teacher getTeacherByName(String name) {
        return teacherMapper.selectTeacherByName(name);
    }

    @Override
    public Integer addTeacherNumber(Teacher teacher) {
        String s = generateTeacherNumber(teacher);
        //teacher.setTeacherNumber(s);
        return teacherMapper.updateTeacherNumber(s,teacher.getTeacherId());
    }

    @Override
    public Integer check() {
        List<Teacher> teachers = teacherMapper.selectAllTeachers();
        for(Teacher teacher : teachers){
            if(teacher.getDepartmentId()==null){
                Department departmentByName = departmentMapper.getDepartmentByName(teacher.getDepartment());
                teacher.setDepartmentId(departmentByName.getDepartmentId());
                Integer i = teacherMapper.updateTeacherDepartment(teacher);
                if(i == null || i<=0){
                    throw new BusinessException(ExceptionType.TEACHER_ERR,"教师更新失败");
                }
            }
            if(teacher.getTeacherNumber() == null){
                String s = generateTeacherNumber(teacher);
                if(s== null){
                    throw new BusinessException(ExceptionType.TEACHER_ERR,"教师工号生成失败");
                }
                teacher.setTeacherNumber(s);
                Integer i = teacherMapper.updateTeacherNumber(s, teacher.getTeacherId());
                if(i == null || i<=0){
                    return 0;
                }
            }
        }
        return 1;
    }

    private Double calcGpa5(double totalScore) {
        // 确保在 [0,100] 范围内
        double s = Math.max(0, Math.min(100, totalScore));
        // 映射并保留两位小数
        double gpa = s / 100.0 * 5.0;
        return Math.round(gpa * 100) / 100.0;
    }
}
