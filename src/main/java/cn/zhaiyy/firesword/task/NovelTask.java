package cn.zhaiyy.firesword.task;

import cn.hutool.core.io.IoUtil;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Log4j2
@Configuration
public class NovelTask {

    @Value("${novel.file.path}")
    private String novelPath;



    public void start(String novelPath, String novelSrcUrl) throws IOException {
        Document doc = Jsoup.connect(novelSrcUrl).get();
        saveFile(novelPath , "head.html", doc.toString());

        Elements navs = doc.select(".nav>a");
        navs.forEach(nav -> {
            String href = nav.attr("href");
            String title = nav.text();
            try {
                Document navDoc = Jsoup.connect(novelSrcUrl + href).get();
                saveFile(novelPath, title + ".html", navDoc.toString());
            } catch (IOException e) {
                log.error("获取连接失败:" + title);
            }
        });
    }

    private void saveFile(String dirPath, String fileName, String content) throws IOException {
        File dir = new File(dirPath);
        dir.mkdirs();
        File file = new File(dirPath, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        IoUtil.writeUtf8(new FileOutputStream(file), true, content);
    }

}
