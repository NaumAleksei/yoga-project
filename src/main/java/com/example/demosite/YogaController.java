package com.example.demosite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class YogaController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user); // Сохраняем в базу данных H2
        return "redirect:/success";
    }

    @GetMapping("/success")
    @ResponseBody
    public String success() {
        return "Вы успешно записались на йогу! Я свяжусь с вами в ближайшее время.";
    }
    @GetMapping("/admin")
    public String adminPanel(Model model) {
        // Достаем всех из базы и кладем в список
        List<User> users = userRepository.findAll();
        // Отправляем этот список в HTML под именем "clients"
        model.addAttribute("clients", users);
        return "admin"; // Будет искать файл admin.html
    }

}