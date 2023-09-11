package triple.fastlms.configuration;

import triple.fastlms.admin.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final LoginHistoryService loginHistoryService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userName = ((UserDetails)authentication.getPrincipal()).getUsername();
        System.out.println(userName);
        loginHistoryService.saveLoginHistory(request, userName);

        setDefaultTargetUrl("/");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
