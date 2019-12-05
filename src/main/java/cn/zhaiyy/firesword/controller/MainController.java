package cn.zhaiyy.firesword.controller;

import cn.zhaiyy.firesword.bean.ControllerResult;
import cn.zhaiyy.firesword.service.NovelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@Configuration
public class MainController {

    @Autowired
    private NovelService novelService;

    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult isAlive() {
        return ControllerResult.succ("OK", null);
    }


    @RequestMapping(value = "/novel_start", method = RequestMethod.GET)
    @ResponseBody
    public ControllerResult novelStart() throws IOException {
        List<NovelService.NovelCategory> categories = novelService.getCategorys();
        novelService.getNovels(categories);
        return ControllerResult.succ("OK", null);
    }
}
