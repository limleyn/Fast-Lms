package triple.fastlms.course.controller;


import triple.fastlms.admin.service.CategoryService;
import triple.fastlms.common.model.ResponseResult;
import triple.fastlms.course.model.ServiceResult;
import triple.fastlms.course.model.TakeCourseInput;
import triple.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiCourseController extends BaseController {
    
    private final CourseService courseService;
    private final CategoryService categoryService;
    
    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model
            , @RequestBody TakeCourseInput parameter
            , Principal principal) {
        
        parameter.setUserId(principal.getName());
    
        ServiceResult result = courseService.req(parameter);
        if (!result.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }
    
        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
    
    
}
