package triple.fastlms.banner.service;

import triple.fastlms.banner.dto.BannerDto;
import triple.fastlms.banner.entity.Banner;
import triple.fastlms.banner.entity.BannerTargetCode;
import triple.fastlms.banner.mapper.BannerMapper;
import triple.fastlms.banner.model.BannerInput;
import triple.fastlms.banner.model.BannerParam;
import triple.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService{

    private final BannerMapper bannerMapper;
    private final BannerRepository bannerRepository;

    @Override
    public List<BannerDto> list(BannerParam parameter) {

        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public List<BannerDto> getBanners() {

        return bannerMapper.selectEnableBanners();

    }

    @Override
    public BannerDto getById(long id) {
        return bannerRepository.findById(id).map(BannerDto::of).orElse(null);
    }

    @Override
    public boolean add(BannerInput parameter) {

        System.out.println(parameter.getBannerTarget());

        if(parameter.getBannerTarget() == null){ //기본값 셋팅.
            parameter.setBannerTarget(BannerTargetCode.BANNER_THIS_WINDOW);
        }

        Banner banner = Banner.builder()
                .subject(parameter.getSubject())
                .targetLink(parameter.getTargetLink())
                .bannerTarget(parameter.getBannerTarget())
                .SortValue(parameter.getSortValue())
                .showYn(parameter.isShowYn())
                .regDt(LocalDateTime.now())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .build();

        bannerRepository.save(banner);

        return true;
    }

    @Override
    public boolean set(BannerInput parameter) {

        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getId());

        if(!optionalBanner.isPresent()){ //수정할 배너가 없다.
            return false;
        }

        if(parameter.getBannerTarget() == null){ //기본값 셋팅.
            parameter.setBannerTarget(BannerTargetCode.BANNER_THIS_WINDOW);
        }

        Banner banner = optionalBanner.get();

        banner.setSubject(parameter.getSubject());
        banner.setTargetLink(parameter.getTargetLink());
        banner.setBannerTarget(parameter.getBannerTarget());
        banner.setSortValue(parameter.getSortValue());
        banner.setShowYn(parameter.isShowYn());
        banner.setUdtDt(LocalDateTime.now());

        if(!parameter.getFilename().trim().equals("")){
            banner.setFilename(parameter.getFilename());
            banner.setUrlFilename(parameter.getUrlFilename());
        }
        bannerRepository.save(banner);

        return true;
    }

    @Override
    public boolean del(String idList) {

        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }

        return true;
    }
}
