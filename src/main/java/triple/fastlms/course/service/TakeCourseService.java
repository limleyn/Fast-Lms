package triple.fastlms.course.service;

import triple.fastlms.course.dto.TakeCourseDto;
import triple.fastlms.course.model.ServiceResult;
import triple.fastlms.course.model.TakeCourseParam;

import java.util.List;

public interface TakeCourseService {

    List<TakeCourseDto> list(TakeCourseParam parameter);

    TakeCourseDto detail(long id);

    ServiceResult updateStatus(long id, String status);

    List<TakeCourseDto> myCourse(String userId);

    ServiceResult cancel(long id);
    
    
    
}
