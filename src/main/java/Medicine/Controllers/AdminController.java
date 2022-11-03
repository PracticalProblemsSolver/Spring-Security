package Medicine.Controllers;

import Medicine.DAO.MedDAOImpl;
import Medicine.Entities.Medicament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@ComponentScan("Medicine")
@RequestMapping("/admin")
public class AdminController {
    private final MedDAOImpl medDAOImpl;

    @Autowired
    public AdminController(MedDAOImpl medDAOImpl) {
        this.medDAOImpl = medDAOImpl;
    }

    @GetMapping("")
    public String mainMenu() {
        return "admin/AdminPanel";
    }

    @GetMapping("/show")
    public String show(Model model) {
        model.addAttribute("meds", medDAOImpl.getMedList());
        return "Show";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("med", new Medicament());
        return "admin/Add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("med") Medicament med, BindingResult result) {
        if(result.hasErrors()){
            return "admin/Add";
        }
        medDAOImpl.addMed(med);
        return "redirect:/admin";
    }

    @GetMapping("/remove")
    public String remove() {
        return "admin/Remove";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(value = "id") int id, Model model){
        if(medDAOImpl.getMedByID(id) == null) {
            String message = "There is no such medicament";
            model.addAttribute("message", message);
            return "admin/Remove";
        }
        medDAOImpl.removeMed(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editID() {
        return "admin/Edit";
    }

    @PostMapping("/edit")
    public String editID(@RequestParam(value = "id") int id, Model model){
        if(medDAOImpl.getMedByID(id) == null) {
            String message = "There is no such medicament";
            model.addAttribute("message", message);
            return "admin/Edit";
        }
        model.addAttribute(medDAOImpl.getMedByID(id));
        return "redirect:/admin/edit/" + id;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("med", medDAOImpl.getMedByID(Integer.parseInt(id)));
        return "admin/EditMed";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable String id, @Valid @ModelAttribute("med") Medicament med, BindingResult result) {
        if(result.hasErrors()){
            return "redirect:/admin/edit/" + id;
        }
        medDAOImpl.updateMed(Integer.parseInt(id),
                med.getName(),
                med.getType(),
                med.getForm(),
                med.getPrice(),
                med.getCount());
        return "redirect:/admin/";
    }

    @GetMapping("/info")
    public String showBiggestCount(Model model) {
        int count = medDAOImpl.findBiggestCount();
        model.addAttribute("count", count);
        return "BiggestCount";
    }
}
