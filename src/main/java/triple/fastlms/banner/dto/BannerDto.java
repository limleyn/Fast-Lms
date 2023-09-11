package triple.fastlms.banner.dto;

import triple.fastlms.banner.entity.Banner;
import triple.fastlms.banner.entity.BannerTargetCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerDto {

    Long id;

    String subject;
    String imagePath;
    String urlFilename;
    String targetLink;
    @Enumerated(EnumType.STRING)
    BannerTargetCode bannerTarget;
    int SortValue;
    boolean showYn;
    LocalDateTime regDt;

    //추가컬럼
    long totalCount;
    long seq;

    public static BannerDto of(Banner banner){

        return BannerDto.builder()
                .id(banner.getId())
                .subject(banner.getSubject())
                .imagePath(banner.getImagePath())
                .urlFilename(banner.getUrlFilename())
                .targetLink(banner.getTargetLink())
                .bannerTarget(banner.getBannerTarget())
                .SortValue(banner.getSortValue())
                .showYn(banner.isShowYn())
                .regDt(banner.getRegDt())
                .build();

    }

}
