package com.example.demo1.controller;
import com.example.demo1.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.example.demo1.dto.CourseDTO;
import com.example.demo1.entity.Course;
import com.example.demo1.service.CourseService;
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;
    @PostMapping("addCourse")
    public ResponseEntity<Result>addCourse(@RequestBody CourseDTO courseDTO){
        boolean success = courseService.addCourse(courseDTO);
        return success ? ResponseEntity.status(200).body(Result.ok("添加成功")) : ResponseEntity.status(200).body(Result.error("添加失败"));
    }
    @GetMapping("selectCourse") //page 表示第几页，limit 表示一页几条
    public ResponseEntity<Result>selectCourse(@RequestParam(required = false,defaultValue = "10") Integer page, @RequestParam(required = false,defaultValue = "10")Integer limit, String courseName, String teacherName){
        PageInfo<Course> pageInfo = courseService.findCourseByName(page, limit, courseName, teacherName);
        return  ResponseEntity.ok(Result.ok("查询成功",pageInfo));
    }
    @GetMapping("deleteCourse")
    public ResponseEntity<Result>deleteCourse(Long id){
        boolean success = courseService.deleteCourse(id);
        return success ? ResponseEntity.status(200).body(Result.ok("删除成功")) : ResponseEntity.status(200).body(Result.error("删除失败"));
    }

}
