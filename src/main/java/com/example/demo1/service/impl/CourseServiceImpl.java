package com.example.demo1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.demo1.dto.CourseDTO;
import com.example.demo1.entity.Course;
import com.example.demo1.mapper.CourseMapper;
import com.example.demo1.service.CourseService;
import com.example.demo1.vo.CourseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public boolean addCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourseName(courseDTO.getCourseName());
        course.setCourseType(courseDTO.getCourseType());
        course.setPayType(courseDTO.getPayType());
        course.setTeacherName(courseDTO.getTeacherName());
        int result = courseMapper.insertCourse(course);
        return result>0;
    }

    @Override
    public PageInfo<Course> findCourseByName(Integer page, Integer limit, String courseName, String teacherName) {
        //开启分页
        PageHelper.startPage(page,limit);
        //开始查询
        List<Course> courseList = courseMapper.selectCourseByName(courseName, teacherName);
        PageInfo pageInfo = new PageInfo(courseList);
        //将List<Course>转换成List<courseVO>
        courseList.stream().map(course -> {
            CourseVO courseVO = new CourseVO();
            BeanUtils.copyProperties(course,courseVO);
            return courseVO;
        }).collect(Collectors.toList());
        return pageInfo;
    }

    @Override
    public boolean deleteCourse(Long id) {
        int result = courseMapper.deleteCourse(id);
        return result>0;
    }
}
