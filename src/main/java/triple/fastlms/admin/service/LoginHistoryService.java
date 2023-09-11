package triple.fastlms.admin.service;

import triple.fastlms.admin.dto.LoginHistoryDto;
import triple.fastlms.admin.model.MemberParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LoginHistoryService {

    void saveLoginHistory(HttpServletRequest request, String userId);
    List<LoginHistoryDto> getLoginList(MemberParam memberParam);
}
