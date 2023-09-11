package triple.fastlms.admin.controller;


import triple.fastlms.admin.dto.MemberDto;
import triple.fastlms.admin.model.MemberParam;
import triple.fastlms.admin.model.MemberInput;
import triple.fastlms.course.controller.BaseController;
import triple.fastlms.admin.dto.LoginHistoryDto;
import triple.fastlms.admin.service.LoginHistoryService;
import triple.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController {
    
    private final MemberService memberService;
    private final LoginHistoryService loginHistoryService;
    
    @GetMapping("/admin/member/list.do")
    public String list(Model model, MemberParam parameter) {
        
        parameter.init();
        List<MemberDto> members = memberService.list(parameter);
        
        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        
        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        
        return "admin/member/list";
    }
    
    @GetMapping("/admin/member/detail.do")
    public String detail(Model model, MemberParam parameter) {
        
        parameter.init();

        //해당되는 멤버 불러오기
        MemberDto member = memberService.detail(parameter.getUserId());
        //해당되는 멤버의 로그인기록 불러오기
        List<LoginHistoryDto> loginHistories = loginHistoryService.getLoginList(parameter);

        long totalCount = 0;

        if(loginHistories != null && loginHistories.size() > 0){
            totalCount = loginHistories.get(0).getTotalCount();
        }

        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), "userId="+parameter.getUserId());

        model.addAttribute("member", member);
        model.addAttribute("loginHistories", loginHistories);
        model.addAttribute("pager", pagerHtml);

        return "admin/member/detail";
    }
    
    @PostMapping("/admin/member/status.do")
    public String status(Model model, MemberInput parameter) {

        boolean result = memberService.updateStatus(parameter.getUserId(), parameter.getUserStatus());
        
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }

    @PostMapping("/admin/member/password.do")
    public String password(Model model, MemberInput parameter) {

        boolean result = memberService.updatePassword(parameter.getUserId(), parameter.getPassword());
        
        return "redirect:/admin/member/detail.do?userId=" + parameter.getUserId();
    }
    
    
    
    
}
