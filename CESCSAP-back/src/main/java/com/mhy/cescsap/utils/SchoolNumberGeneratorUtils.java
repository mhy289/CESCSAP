package com.mhy.cescsap.utils;

import com.mhy.cescsap.pojo.Class;
import com.mhy.cescsap.pojo.Student;
import com.mhy.cescsap.pojo.Teacher;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SchoolNumberGeneratorUtils {

    //private static final DateTimeFormatter YEAR_FORMAT = DateTimeFormatter.ofPattern("yyyy");

    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    private static final int MAX_SEQUENCE = 99;
    private static final int MAX_CLASS_CODE = 99;

    // 学生计数器：key=入校时间+院系+班级，value=序号计数器
    private static final ConcurrentHashMap<String, AtomicInteger> STUDENT_COUNTER = new ConcurrentHashMap<>();
    // 教师计数器：key=入校时间+院系，value=序号计数器（班级固定00）
    private static final ConcurrentHashMap<String, AtomicInteger> TEACHER_COUNTER = new ConcurrentHashMap<>();

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
        String departmentCode = getDepartmentCode(student.getDepartment());
        String classCode = getClassCode(clazz.getClassId());

        // 生成唯一学号
        String key = yearPart + departmentCode + classCode;
        AtomicInteger counter = STUDENT_COUNTER.computeIfAbsent(key, k -> new AtomicInteger(0));
        int sequence = counter.incrementAndGet();

        return String.format("%s%s%s%02d", yearPart, departmentCode, classCode, sequence);
    }

    //region 私有校验和转换方法
    private static void validateStudent(Student student, Class clazz) {
        if (student == null || clazz == null) {
            throw new NullPointerException("学生或班级信息不可为空");
        }
        if (student.getEnrollDate() == null) {
            throw new IllegalArgumentException("入学日期不可为空");
        }
        if (student.getDepartment() == null || student.getDepartment().length() != 2) {
            throw new IllegalArgumentException("院系代码必须为2位数字：" + student.getDepartment());
        }
        if (clazz.getClassId() == null || clazz.getClassId() < 0 || clazz.getClassId() > 99) {
            throw new IllegalArgumentException("班级ID必须为0-99的整数：" + clazz.getClassId());
        }
    }

    private static String getYearPart(Date date) {
        return YEAR_FORMAT.format(date);
    }

    private static String getDepartmentCode(String department) {
        // 假设院系代码直接使用Student的department字段（需确保2位数字）
        if (!department.matches("\\d{2}")) {
            throw new IllegalArgumentException("院系代码格式错误：" + department);
        }
        return department;
    }

    private static String getClassCode(Long classId) {
        // 班级代码使用classId后两位（自动补零）
        return String.format("%02d", classId % 100);
    }

    /**
     * 生成教师工号（10位：YYYYDD00SS）
     * @param teacher 教师实体
     * @return 生成的工号
     * @throws IllegalArgumentException 参数格式错误
     */
    public static String generateTeacherNumber(Teacher teacher) {
        Date hireDate = teacher.getHireDate();
        String departmentCode = getDepartmentCode(teacher.getDepartment());
        validateCode("院系代码", departmentCode, 2);
        String yearPart = YEAR_FORMAT.format(hireDate);
        String key = yearPart + departmentCode;

        AtomicInteger counter = TEACHER_COUNTER.computeIfAbsent(key, k -> new AtomicInteger(0));
        int sequence = counter.incrementAndGet();
        if (sequence > MAX_SEQUENCE) throw new IllegalStateException("教师序号已达上限：" + MAX_SEQUENCE);

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

    // 测试示例
    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 构造测试数据
            Student student = new Student();
            student.setEnrollDate(sdf.parse("2025-09-01"));
            student.setDepartment("03"); // 计算机学院

            Class clazz = new Class();
            clazz.setClassId(1L); // 班级ID 123 -> 班级代码 "23"

            // 生成学号（2025级03院系23班第1位学生）
            String studentNo = generateStudentNumber(student, clazz); // 2025032301
            System.out.println("优化后学号：" + studentNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
