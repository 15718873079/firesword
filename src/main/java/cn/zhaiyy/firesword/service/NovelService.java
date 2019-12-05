package cn.zhaiyy.firesword.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class NovelService {

    @Value("${novel.file.path}")
    private String novelPath;
    @Value("${novel.src.url}")
    private String novelSrcUrl;

    @Autowired
    private ZSetOperations<String, String> zSetOperations;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<NovelCategory> getCategorys() throws IOException {
        Document doc = Jsoup.connect(novelSrcUrl).get();
        Elements navs = doc.select(".nav>a");

        List<NovelCategory> categorys = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);
        navs.forEach(nav -> {
            String href = nav.attr("href");
            String title = nav.text();
            int dbKey = i.getAndIncrement();
            NovelCategory category = new NovelCategory(title, href, dbKey);
            categorys.add(category);
        });
        log.info("获取到的分类有:" + JSON.toJSONString(categorys));
        return categorys;
    }

    public void getNovels(List<NovelCategory> novelCategories) {
        ExecutorService executorService = Executors.newFixedThreadPool(novelCategories.size());
        for (NovelCategory novelCategory : novelCategories) {
            executorService.submit(()->{
                log.info(JSON.toJSONString(novelCategory));
                Long flag = redisTemplate.opsForSet().add("novel.category." + novelCategory.getDbKey(), JSON.toJSONString(novelCategory));
                log.info(flag);
            });
        }
        executorService.shutdown();
    }

    public static class NovelCategory {
        private String title;
        private String href;
        private int dbKey;

        public NovelCategory(String title, String href, int dbKey) {
            this.title = title;
            this.href = href;
            this.dbKey = dbKey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getDbKey() {
            return dbKey;
        }

        public void setDbKey(int dbKey) {
            this.dbKey = dbKey;
        }
    }
}
