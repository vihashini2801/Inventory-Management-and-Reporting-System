package com.enterprise.inventory.controller;

import com.enterprise.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ProductService productService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalProducts",
                productService.getTotalProductCount());

        model.addAttribute("totalInventoryValue",
                productService.getTotalInventoryValue());

        model.addAttribute("lowStockCount",
                productService.getLowStockCount());
        model.addAttribute("topProducts",
                productService.getTopProducts());

        model.addAttribute("recentLogs",
                productService.getRecentLogs());

        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/analytics")
    public String analytics(){
        return "analytics";
    }
    @GetMapping("/settings")
    public String settings(){
        return "settings";
    }
}