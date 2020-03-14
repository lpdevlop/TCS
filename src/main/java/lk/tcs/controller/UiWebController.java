package lk.tcs.controller;


import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/web")
public class UiWebController {

    @RequestMapping("/index")
    public ModelAndView myui(@CookieValue(value="username") String username, @CookieValue(value="password") String password){
        return getModelView("index.html",username,password);
    }

    public ModelAndView getModelView(String page,String username, String password){

        ModelAndView model = new ModelAndView();

        if(AuthProvider.isUser(username,password)) {

            model.setViewName(page);
        }
        else {
            model.setViewName("noprivilage.html");

        }
        return model;

    }

}
