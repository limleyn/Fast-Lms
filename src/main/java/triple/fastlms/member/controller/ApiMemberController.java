package triple.fastlms.member.controller;


import triple.fastlms.common.model.ResponseResult;
import triple.fastlms.course.dto.TakeCourseDto;
import triple.fastlms.course.model.ServiceResult;
import triple.fastlms.course.model.TakeCourseInput;
import triple.fastlms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiMemberController {
    
    private final TakeCourseService takeCourseService;
    
    
    @PostMapping("/api/member/course/cancel.api")
    public ResponseEntity<?> cancelCourse(Model model
            , @RequestBody TakeCourseInput parameter
            , Principal principal) {
        
        String userId = principal.getName();
        
        //내 강좌인지 확인
        TakeCourseDto detail = takeCourseService.detail(parameter.getTakeCourseId());
        if (detail == null) {
            ResponseResult responseResult = new ResponseResult(false, "수강 신청 정보가 존재하지 않습니다.");
            return ResponseEntity.ok().body(responseResult);
        }
        
        if (userId == null || !userId.equals(detail.getUserId())) {
            ResponseResult responseResult = new ResponseResult(false, "본인의 수강 신청 정보만 취소할 수 있습니다.");
            return ResponseEntity.ok().body(responseResult);
        }
        
        ServiceResult result = takeCourseService.cancel(parameter.getTakeCourseId());
        if (!result.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }
    
        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
    
    
}
