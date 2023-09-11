package triple.fastlms.course.controller;


import triple.fastlms.admin.service.CategoryService;
import triple.fastlms.course.dto.CourseDto;
import triple.fastlms.course.model.CourseInput;
import triple.fastlms.course.model.CourseParam;
import triple.fastlms.course.service.CourseService;
import triple.fastlms.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController {
    
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final String baseLocalPath = Paths.get("files").toAbsolutePath().toString();
    
    @GetMapping("/admin/course/list.do")
    public String list(Model model, CourseParam parameter) {
        
        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);
        
        long totalCount = 0;
        if (!CollectionUtils.isEmpty(courseList)) {
            totalCount = courseList.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        
        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        
        return "admin/course/list";
    }
    
    @GetMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String add(Model model, HttpServletRequest request
            , CourseInput parameter) {
        
        //카테고리 정보를 내려줘야 함.
        model.addAttribute("category", categoryService.list());
        
        boolean editMode = request.getRequestURI().contains("/edit.do");
        CourseDto detail = new CourseDto();
        
        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existCourse;
        }
        
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
        
        return "admin/course/add";
    }
    
    @PostMapping(value = {"/admin/course/add.do", "/admin/course/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request
                            , MultipartFile file
            , CourseInput parameter) {
    
        String saveFilename = "";
        String urlFilename = "";
        
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();

            String baseUrlPath = "/files";
            
            String[] arrFilename = FileUtils.getNewSaveFile(originalFilename);
    
            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];
            
            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("############################ - 1");
                log.info(e.getMessage());
            }
        }
        
        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);
        
        boolean editMode = request.getRequestURI().contains("/edit.do");
        
        if (editMode) {
            long id = parameter.getId();
            CourseDto existCourse = courseService.getById(id);
            if (existCourse == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            
            boolean result = courseService.set(parameter);
            
        } else {
            boolean result = courseService.add(parameter);
        }
        
        return "redirect:/admin/course/list.do";
    }
    
    @PostMapping("/admin/course/delete.do")
    public String del(Model model, HttpServletRequest request
            , CourseInput parameter) {
        
        boolean result = courseService.del(parameter.getIdList());
        
        return "redirect:/admin/course/list.do";
    }
    
    
}