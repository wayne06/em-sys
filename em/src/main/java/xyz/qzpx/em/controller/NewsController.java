package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.NewsDO;
import xyz.qzpx.em.service.NewsService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/add")
    public void add(@RequestBody NewsDO newsDO) {
        newsService.add(newsDO);
    }

    @PostMapping("/del")
    public void del(@RequestBody NewsDO newsDO) {
        newsService.del(newsDO);
    }

    @PostMapping("/update")
    public void update(@RequestBody NewsDO newsDO) {
        newsService.update(newsDO);
    }

    @GetMapping("/all")
    public List<NewsDO> all() {
        return newsService.getAll();
    }

    @GetMapping("/allForHomepage")
    public List<NewsDO> allForHomepage() {
        return newsService.allForHomepage();
    }

}
