package triple.fastlms.course.mapper;

import triple.fastlms.course.dto.CourseDto;
import triple.fastlms.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    
    long selectListCount(CourseParam parameter);
    List<CourseDto> selectList(CourseParam parameter);
    
}
