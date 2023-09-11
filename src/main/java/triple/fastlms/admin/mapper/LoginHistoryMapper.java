package triple.fastlms.admin.mapper;


import triple.fastlms.admin.model.MemberParam;
import triple.fastlms.admin.dto.LoginHistoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginHistoryMapper {

    long selectListCount(MemberParam parameter);
    List<LoginHistoryDto> selectList(MemberParam parameter);

}
