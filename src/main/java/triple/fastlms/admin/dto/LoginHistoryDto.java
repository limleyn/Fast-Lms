package triple.fastlms.admin.dto;

import triple.fastlms.admin.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginHistoryDto {

    LocalDateTime loginDt;
    String ip;
    String userAgent;

    //for paging column
    long totalCount;
    long seq;

    public static List<LoginHistoryDto> of(List<LoginHistory> loginHistories){
        if(loginHistories != null){
            return loginHistories.stream().map(LoginHistoryDto::of)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public static LoginHistoryDto of(LoginHistory loginHistory){
        return LoginHistoryDto.builder()
                .loginDt(loginHistory.getLoginDt())
                .ip(loginHistory.getIp())
                .userAgent(loginHistory.getUserAgent())
                .build();
    }
}
