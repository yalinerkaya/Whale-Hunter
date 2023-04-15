package com.example.whalehunter.controller;

import com.example.whalehunter.model.coin.CoinData;
import com.example.whalehunter.service.coin.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoinController {

    private CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping("/top3Coins")
    public String getTop3Coins(Model model) throws Exception {
        List<CoinData> coins = coinService.selectTop3coins();
        model.addAttribute("coins", coins);
        return "top3Coins";
    }
}
