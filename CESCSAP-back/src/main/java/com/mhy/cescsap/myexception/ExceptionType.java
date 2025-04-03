package com.mhy.cescsap.myexception;

public enum ExceptionType {
    SYS_NOT_FOUND,
    SYS_ERROR,
    BUS_ID_NOT_FOUND,
    BUS_INTEGER_ABC,
    BUS_INVALID_KEY,
    BUS_DELETED_EXIST_DATA,
    //参数传递失败
    PARAM_PASS_ERROR,
    //SQL 执行出错
    BUS_DELETED_SQL_NUM_ERROR,
    SERVER_ERROR,
    BUS_INVALID_DATA,
    STUDENT_ID_NOT_FOUND,
    VALIDATION_ERROR,
    AUTHENTICATION_ERROR,
    FORBIDDEN_ERROR, BUS_INVALID_TOKEN, DISH_NOT_FOUND, DISH_ADD_FALSE, CART_NOT_FOUND, DISH_UPDATE_FALSE, SYS_METHOD_MISSED, USER_FOUND, USER_NOT_FOUND, EVAL_ERROR, STUDENT_NAME_ERROR, EVAL_SAVE_ERR, EVAL_STATISTICS_ERR, EVAL_DTL_ERR, STU, ERR, STU_ERR, TEACHER_ERR, COURSE_ERR, EVAL_DIM_ERR, EVALUATE_ERR, INTERNAL_ERROR;
}
