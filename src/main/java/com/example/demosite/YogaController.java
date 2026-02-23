package com.example.demosite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.http.ResponseEntity;

@Controller
public class YogaController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "index";
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

    @Autowired
    private JavaMailSender mailSender;

    // Оставляем ТОЛЬКО один метод на этот адрес
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestParam String name,
                                           @RequestParam String email,
                                           @RequestParam String phone) {
        try {
            // 1. ЗДЕСЬ МОЖНО СОХРАНИТЬ В БАЗУ (если нужно)
            // User user = new User(name, email, phone);
            // userRepository.save(user);

            // 2. ОТПРАВКА ПИСЬМА
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mvnaumova@mail.ru");
            message.setTo("mvnaumova@mail.ru");
            message.setSubject("Новая запись: " + name);
            message.setText("Данные клиента:\nИмя: " + name + "\nEmail: " + email + "\nТелефон: " + phone);

            mailSender.send(message);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }



}