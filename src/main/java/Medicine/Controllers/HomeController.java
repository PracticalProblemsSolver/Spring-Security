package Medicine.Controllers;

import Medicine.DAO.UserDAOImpl;
import Medicine.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class HomeController {
    public final UserDAOImpl userDAO;

    @Autowired
    public HomeController(UserDAOImpl userDAO){
        this.userDAO = userDAO;
    }

    @GetMapping("/")
    public String redirectToPage(HttpServletRequest httpServletRequest){
        if(httpServletRequest.isUserInRole("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/user";
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new User());
        return "Login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("user", new User());
        return "Registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(result.hasErrors()) {
            System.out.println("1");
            return "Registration";
        }
        if(userDAO.userExist(user.getUsername())) {
            System.out.println("2");
            String message = "This login already exists";
            model.addAttribute("message", message);
            return "Registration";
        }
        user.setRole("ROLE_USER");
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.addUser(user);
        return "redirect:/login";
    }

}
