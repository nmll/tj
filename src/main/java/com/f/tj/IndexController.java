package com.f.tj;

import com.f.tj.entity.Goods;
import com.f.tj.repository.GoodsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

/**
 * Created by fei on 2017/4/12.
 */
@Controller
public class IndexController {

    String items;
    JsonNode datas;

    ObjectMapper om = new ObjectMapper();

    @Autowired
    GoodsRepository goodsRepository;

    @RequestMapping("data")
    public @ResponseBody
    Map<String, String> title(String data) throws IOException {
        datas = om.readTree(data);
        Iterator<JsonNode> items = datas.elements();
        List<Goods> list = new ArrayList<Goods>();
        int i=0;
        while (items.hasNext()) {
            JsonNode item = items.next();
            if (item.get("name") != null) {
                try {
                    String price=item.get("price").asText();
                    String title = Title.getTitle(item.get("name").asText(), price);
                    Goods goods = new Goods();
                    goods.setId(item.get("id").asText());
                    goods.setTbName(title);
                    goods.setPrice(price);
                    list.add(goods);
                }catch (IOException e){
                    i++;
                }
            }
        }
        goodsRepository.save(list);
        Map<String, String> map=new HashMap<String, String>();
        map.put("message","失败次数:"+i);
        return map;
    }

    @RequestMapping("name")
    public @ResponseBody
    String title(String name, String price) throws IOException {
        return Title.getTitle(name, price);
    }


}
