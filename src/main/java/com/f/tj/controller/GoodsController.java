package com.f.tj.controller;

import com.f.tj.entity.Goods;
import com.f.tj.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by fei on 2017/7/10.
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsRepository goodsRepository;

    @RequestMapping("")
    public @ResponseBody
    Iterable<Goods> find(HttpServletResponse response)  {
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        return goodsRepository.findGoods();
    }

    @RequestMapping("update")
    public @ResponseBody
    int update()  {
        return goodsRepository.updateName();
    }
}
