package triple.fastlms.banner.service;

import triple.fastlms.banner.dto.BannerDto;
import triple.fastlms.banner.model.BannerInput;
import triple.fastlms.banner.model.BannerParam;

import java.util.List;

public interface BannerService {

    List<BannerDto> list(BannerParam parameter);

    List<BannerDto> getBanners();

    BannerDto getById(long id);

    boolean add(BannerInput parameter);

    boolean set(BannerInput parameter);

    boolean del(String idList);
}
