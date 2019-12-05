package cn.zhaiyy.firesword.controller;

import cn.zhaiyy.firesword.bean.ControllerResult;
import cn.zhaiyy.firesword.service.IPProxyService;
import cn.zhaiyy.firesword.service.NovelService;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

@Log4j2
@RestController
@Configuration
public class MainController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private IPProxyService ipProxyService;

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult isAlive() {
        return ControllerResult.succ("OK", null);
    }


    @RequestMapping(value = "/novel/pull", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult novelStart() throws IOException {
        List<NovelService.NovelCategory> categories = novelService.getCategorys();
        novelService.getNovels(categories);
        return ControllerResult.succ("OK", null);
    }

    @RequestMapping(value = "/ip/print", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult getIP(HttpServletRequest request) {
        log.info("获取用户IP：" + request.getRemoteAddr());
        return null;
    }

    @RequestMapping(value = "/ip/send", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult sendIP(String ip, int port) throws IOException {
        log.info(ip);
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            Jsoup.connect("http://localhost:7777/ip/print").proxy(proxy).timeout(40*1000).get();
        } catch (IOException e) {
            log.error(e.getMessage());
            return ControllerResult.fail(e.getMessage(), ip);
        }
        return ControllerResult.succ("OK", ip);
    }

    @RequestMapping(value = "/ip/pull", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult pullIPProxy(HttpServletRequest request) throws IOException {
        ipProxyService.pullIPProxy();
        return null;
    }

}
