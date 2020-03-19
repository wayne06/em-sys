package xyz.qzpx.em.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.qzpx.em.dataObject.LessonDO;
import xyz.qzpx.em.service.LessonService;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/add")
    public LessonDO add(@RequestBody LessonDO lessonDO) {
        lessonService.addLesson(lessonDO);
        return lessonDO;
    }

    @PostMapping("/del")
    public void delete(@RequestBody LessonDO lessonDO) {
        lessonService.deleteLessonById(lessonDO.getId());
    }

    @PostMapping("/delbatch")
    public void deleteBatch(@RequestBody LessonDO lessonDO) {
        String ids = lessonDO.getRemark();
        String[] idStrs = ids.split(",");
        Integer[] idInts = new Integer[idStrs.length];
        for (int i = 0; i < idStrs.length; i++) {
            idInts[i] = Integer.parseInt(idStrs[i]);
        }
        lessonService.deleteLessonsByIds(idInts);
    }

    @PostMapping("/update")
    public LessonDO update(@RequestBody LessonDO lessonDO) {
        lessonService.updateLesson(lessonDO);
        return lessonDO;
    }

    @GetMapping("/all")
    public List<LessonDO> list() {
        return lessonService.getAllLesson();
    }

    @GetMapping("/count")
    public Integer getCount() {
        return lessonService.getLessonCount();
    }


}
