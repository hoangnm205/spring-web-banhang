/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.demo.web.spring01.controllers;

import com.java.demo.web.spring01.dto.Customer;
import com.java.demo.web.spring01.dto.LoginInfo;
import com.java.demo.web.spring01.dto.Product;
import com.java.demo.web.spring01.dto.Slider;
import com.java.demo.web.spring01.model.CustomerModel;
import com.java.demo.web.spring01.model.ProductModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


/**
 *
 * @author hoangnghiem
 */
@Controller
@SessionAttributes("user")
public class HomeController {

    private List<Slider> getSliders() {
        List<Slider> sliders = new ArrayList<>();
        sliders.add(new Slider(1,
                "SLIDER 01",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "https://picsum.photos/seed/picsum/800/400")
        );
        sliders.add(new Slider(2,
                "SLIDER 02",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "https://picsum.photos/seed/picsum/800/400")
        );
        sliders.add(new Slider(3,
                "SLIDER 03",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "https://picsum.photos/seed/picsum/800/400")
        );
        sliders.add(new Slider(4,
                "SLIDER 04",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "https://picsum.photos/seed/picsum/800/400")
        );
        
        return sliders;
    }

    @RequestMapping("/loginHandle")
    public String login(@ModelAttribute LoginInfo loginInfo, Model model) {
        try {
            CustomerModel customerModel = new CustomerModel();
            Customer c = customerModel.login(loginInfo.getUsername(), loginInfo.getPassword());
            if (c != null) { // login thanh cong
                // luu vao session
                model.addAttribute("user", loginInfo.getUsername());
            } else {
                return "redirect:/login";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
    
    @RequestMapping("/")
    public String home(Model model) {
        try {
            String title = "TIEU DE";
            model.addAttribute("title", title);
            model.addAttribute("sliders", this.getSliders());
            
            // Lấy ra 3 sp hot
            ProductModel prodModel = new ProductModel();
            List<Product> hotProducts = prodModel.getHotProduct(3);
            model.addAttribute("hotProducts", hotProducts);
            
            // Lấy ra 3 sp mới
            List<Product> newProducts = prodModel.getNewProduct(3);
            model.addAttribute("newProducts", newProducts);
            
            // Lấy ra 3 sp gợi ý
            List<Product> randProducts = prodModel.getRandProduct(3);
            model.addAttribute("randProducts", randProducts);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "home";
    }

    @RequestMapping("/notice")
    public String notice() {
        return "notice";
    }

    @RequestMapping("/login")
    public String login(Model model, @CookieValue(name = "email", value = "") String email) {
        model.addAttribute("loginInfo", new LoginInfo(email, ""));
        return "login";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "cart";
    }
}
