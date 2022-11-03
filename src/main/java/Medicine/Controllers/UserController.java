package Medicine.Controllers;

import Medicine.DAO.MedDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@ComponentScan("Medicine")
@RequestMapping("/user")
public class UserController {
    private final MedDAOImpl medDAOImpl;

    @Autowired
    public UserController(MedDAOImpl medDAOImpl) {
        this.medDAOImpl = medDAOImpl;
    }

    @GetMapping("")
    public String mainMenu() {
        return "UserPanel";
    }

    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("meds", medDAOImpl.getMedList());
        return "Show";
    }

    @GetMapping("/info")
    public String showBiggestCount(Model model) {
        int count = medDAOImpl.findBiggestCount();
        model.addAttribute("count", count);
        return "BiggestCount";
    }
}
