package com.f.tj;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @RequestMapping("data")
    public @ResponseBody
    List<Map<String, String>> title(String data) throws IOException {
        datas = om.readTree(data);
        Iterator<JsonNode> items = datas.elements();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        while (items.hasNext()) {
            JsonNode item = items.next();
            if (item.get("name") != null) {
                String title = Title.getTitle(item.get("name").asText(), item.get("price").asText());
                Map<String, String> map = new HashMap<String, String>();
                map.put("id", item.get("id").asText());
                map.put("name", title);
                list.add(map);
            }
        }
        return list;
    }

    @RequestMapping("name")
    public @ResponseBody
    String title(String name, String price) throws IOException {
        return Title.getTitle(name, price);
    }


}
