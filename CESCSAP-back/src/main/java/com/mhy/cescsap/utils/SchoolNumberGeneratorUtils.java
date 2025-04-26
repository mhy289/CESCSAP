package com.mhy.cescsap.utils;

import com.mhy.cescsap.mapper.StudentMapper;
import com.mhy.cescsap.mapper.TeacherMapper;
import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class SchoolNumberGeneratorUtils {

    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    private static final int MAX_CLASS_CODE = 99;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 基于Student和Class对象生成学号（对象化版本）
     * @param student 学生实体
     * @param clazz 班级实体
     * @return 生成的学号（格式：YYYYDDCCSS）
     * @throws IllegalArgumentException 关键信息缺失或格式错误
     */
    public static String generateStudentNumber(Student student, Class clazz) {
        // 校验关键信息
        validateStudent(student, clazz);

        // 提取各部分字段
        String yearPart = getYearPart(student.getEnrollDate());
        String departmentCode = getDepartmentCode(student.getDepartmentId());
        String classCode = getClassCode(clazz.getClassId());
        Long studentId = student.getStudentId();
        if (studentId == null) {
            throw new IllegalArgumentException("未找到该学生的 ID");
        }
        int sequence = (int) (studentId % 2000);

        return String.format("%s%s%s%02d", yearPart, departmentCode, classCode, sequence);
    }

    //region 私有校验和转换方法
    private static void validateStudent(Student student, Class clazz) {
        if (student == null || clazz == null) {
            throw new NullPointerException("学生或班级信息不可为空");
        }
        if (student.getEnrollDate() == null) {
            student.setEnrollDate(new Date());
        }
        if (student.getDepartment() == null || student.getDepartment().length() != 2) {
            //throw new IllegalArgumentException("院系代码必须为 2 位数字：" + student.getDepartment());
            log.debug("院系代码必须为 2 位数字：" + student.getDepartment());
        }
        if (clazz.getClassId() == null || clazz.getClassId() < 0 || clazz.getClassId() > 99) {
            throw new IllegalArgumentException("班级 ID 必须为 0 - 99 的整数：" + clazz.getClassId());
        }
    }

    private static String getYearPart(Date date) {
        return YEAR_FORMAT.format(date);
    }

    private static String getDepartmentCode(Long departmentId)  {
        // 假设院系代码直接使用 Student 的 department 字段（需确保 2 位数字）
        return String.format("%02d", departmentId % 100);
    }

    private static String getClassCode(Long classId) {
        // 班级代码使用 classId 后两位（自动补零）
        return String.format("%02d", classId % 100);
    }

    /**
     * 生成教师工号（10 位：YYYYDD00SS）
     * @param teacher 教师实体
     * @return 生成的工号
     * @throws IllegalArgumentException 参数格式错误
     */
    public static String generateTeacherNumber(Teacher teacher) {
        Date hireDate = teacher.getHireDate();
        if (hireDate == null) {
            hireDate = new Date();
        }
        String departmentCode = getDepartmentCode(teacher.getDepartmentId());
        validateCode("院系代码", departmentCode, 2);
        String yearPart = YEAR_FORMAT.format(hireDate);

        // 从数据库中获取教师 ID 并对 2000 取模
        Long teacherId = teacher.getTeacherId();
        if (teacherId == null) {
            throw new IllegalArgumentException("未找到该教师的 ID");
        }
        int sequence = (int) (teacherId % 2000);

        return String.format("%s%s00%02d",
                yearPart,
                departmentCode,
                sequence);
    }

    private static void validateCode(String type, String code, int length) {
        if (code == null || code.length() != length || !code.matches("\\d+")) {
            throw new IllegalArgumentException(type + "必须为" + length + "位纯数字：" + code);
        }
    }
}