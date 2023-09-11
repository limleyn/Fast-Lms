package triple.fastlms.banner.mapper;

import triple.fastlms.banner.dto.BannerDto;
import triple.fastlms.banner.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    
    long selectListCount(BannerParam parameter);
    List<BannerDto> selectList(BannerParam parameter);
    List<BannerDto> selectEnableBanners();
    
}
