package triple.fastlms.admin.mapper;


import triple.fastlms.admin.dto.MemberDto;
import triple.fastlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    long selectListCount(MemberParam parameter);
    List<MemberDto> selectList(MemberParam parameter);

}
