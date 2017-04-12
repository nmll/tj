package com.f.tj;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fei on 2017/4/12.
 */
public class Title {


    static String getTitle(String name, String price) throws IOException {
        String url="https://s.taobao.com/search?q="+name+"&style=list&filter=reserve_price%5B"+price+"%2C"+price+"%5D&sort=sale-desc";
        System.out.println(url);
        Request request = Request.Get(url);
        String data = request.execute().returnContent().asString();
        Pattern pattern = Pattern.compile("g_page_config = (.*)");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            String sdata = matcher.group(1);
            System.out.println(sdata);
            ObjectMapper om = new ObjectMapper();
            JsonNode jn = om.readTree(sdata);
            JsonNode x = jn.get("mods").get("itemlist").get("data").get("auctions");
            Iterator<JsonNode> ir = x.elements();
            while (ir.hasNext()) {
                JsonNode goods = ir.next();
                Iterator<JsonNode> icons = goods.get("icon").elements();
                boolean isc = false;
                while (icons.hasNext()) {
                    JsonNode icon = icons.next();
                    if ("平台代充".equals(icon.get("title").asText())) {
                        isc = true;
                        break;
                    }
                }
                System.out.println(isc);
                if (isc) {
                    String title = goods.get("raw_title").asText();
                    String vprice = goods.get("view_price").asText();
                    if (vprice.equals(price)) {
                        System.out.println(goods.get("raw_title").asText());
                        System.out.println(goods.get("detail_url").asText());
                        return title;
                    }
                }
            }

        }
        return "";
    }

}
