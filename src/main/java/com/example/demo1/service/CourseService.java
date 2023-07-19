package com.example.demo1.service;



import com.github.pagehelper.PageInfo;
import com.example.demo1.dto.CourseDTO;
import com.example.demo1.entity.Course;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    boolean addCourse(CourseDTO courseDTO);

    PageInfo<Course> findCourseByName(Integer page, Integer limit, String courseName, String teacherName);

    boolean deleteCourse(Long id);
}
