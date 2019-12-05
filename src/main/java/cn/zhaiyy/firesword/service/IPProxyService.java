package cn.zhaiyy.firesword.service;

import cn.zhaiyy.firesword.bean.IPProxyBean;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class IPProxyService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String KEY_IP_PROXY = "ip.proxy";

    public void pullIPProxy() throws IOException {
        Document doc = Jsoup.connect("https://www.kuaidaili.com/free").get();
        Elements trs = doc.select("tr");
        trs.forEach((trElement) -> {
            Elements tdElements = trElement.select("td");
            if (tdElements.size() < 7) {
                return;
            }
            String ip = tdElements.get(0).text();
            String port = tdElements.get(1).text();
            String type = tdElements.get(3).text();
            String address = tdElements.get(4).text();
            String speed = tdElements.get(5).text();
            String lastValidTime = tdElements.get(6).text();
            speed = speed.replaceAll("\\D", "");
            IPProxyBean ipProxyBean = new IPProxyBean(ip, Integer.parseInt(port), type, address, Integer.valueOf(speed), lastValidTime);
            if (validProxy(ipProxyBean)) {
                redisTemplate.opsForZSet().add(KEY_IP_PROXY, JSON.toJSONString(ipProxyBean), 100);
            }
        });


    }

    private boolean validProxy(IPProxyBean ipProxyBean) {
        log.info(ipProxyBean.getIp());
        try {
            Jsoup.connect("http://localhost:7777/ip/print")
                    .proxy(ipProxyBean.getIp(), ipProxyBean.getPort())
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return true;
    }

}
