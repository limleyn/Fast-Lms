package triple.fastlms.course.model;

import triple.fastlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {

    long id;
    String status;
    
    String userId;
    
    
    long searchCourseId;
}
