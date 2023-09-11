package triple.fastlms.main.controller;


import triple.fastlms.banner.dto.BannerDto;
import triple.fastlms.banner.service.BannerService;
import triple.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final BannerService bannerService;
    
    @RequestMapping("/")
    public String index(Model model) {

        List<BannerDto> banners = bannerService.getBanners();

        model.addAttribute("banners", banners);

        return "index";
    }
    
    
    
    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
    
    
    
}
