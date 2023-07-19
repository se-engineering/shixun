package com.example.demo1.mapper;


import com.example.demo1.entity.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper {
    @Insert("insert into course (course_name,teacher_name,course_type,pay_type) values (#{courseName},#{teacherName},#{courseType},#{payType})")
    int insertCourse(Course course);
    @Select("<script>" +
            "select id,course_name,teacher_name,course_type,pay_type,state from course"+
            "<where>" +
            "<if test ='courseName!=null and courseName !=\" \"'>" +
            " and course_name LIKE '%${courseName}%'" +
            "</if>"+
            "<if test ='teacherName!=null and teacherName !=\" \"'>" +
            " and teacher_name LIKE '%${teacherName}%'" +
            "</if>"+
            "and state = 0"+
            "</where>"+
            "</script>")
    List<Course> selectCourseByName(String courseName, String teacherName);
    @Update("update course set state = 1 where id = #{id}")
    int deleteCourse(Long id);
}

