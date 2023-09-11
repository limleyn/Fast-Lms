package triple.fastlms.course.model;

import triple.fastlms.admin.model.CommonParam;
import lombok.Data;

@Data
public class CourseParam extends CommonParam {

    long id;//course.id
    long categoryId;

}
