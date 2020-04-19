package xyz.qzpx.em.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseDOMapper;
import xyz.qzpx.em.dao.CourseStudentDOMapper;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.*;
import xyz.qzpx.em.service.CourseStudentService;

import java.util.*;

@Service
public class CourseStudentServiceImpl implements CourseStudentService {

    @Autowired
    private CourseStudentDOMapper courseStudentDOMapper;

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Autowired
    private CourseDOMapper courseDOMapper;

    @Override
    public List<String> getStudentIdsByCourseId(Integer id) {
        //courseStudentDOMapper.selectByCourseId(id);
        return null;
    }

    @Override
    public List<String> getStudentIdsNotInCourseByCourseId(Integer id) {
        //courseStudentDOMapper.selectOthersByCourseId(id);
        return null;
    }

    @Override
    public void addSingleStuToCourse(Integer courseId, Integer stuId) {
        CourseStudentDO courseStudentDO = new CourseStudentDO();
        courseStudentDO.setCourseId(courseId);
        courseStudentDO.setStudentId(stuId);
        courseStudentDO.setCreatedAt(new Date());
        courseStudentDO.setUpdatedAt(new Date());
        courseStudentDOMapper.insert(courseStudentDO);
    }

    @Override
    public List<Integer> getStudentIdsNotInCourse(Integer id) {
        List<Integer> allId = studentDOMapper.selectAllId();
        Map<Integer, List<Integer>> stu_cor_map = new HashMap<>(allId.size());
        for (Integer i : allId) {
            stu_cor_map.put(i, new ArrayList<>());
        }

        List<CourseStudentDO> courseStudentDOS = null;
        //courseStudentDOS = courseStudentDOMapper.selectIds()
        for (CourseStudentDO c : courseStudentDOS) {
            stu_cor_map.get(c.getStudentId()).add(c.getCourseId());
        }

        List<Integer> notInList = new ArrayList<>(stu_cor_map.size());
        for (Map.Entry<Integer, List<Integer>> entry : stu_cor_map.entrySet()) {
            if (!entry.getValue().contains(id)) {
                notInList.add(entry.getKey());
            }
        }
        return notInList;
    }

    @Override
    public void rmStuToCourse(Integer courseId, Integer stuId) {
        //courseStudentDOMapper.deleteByCourseIdAndStudentId(courseId, stuId);
    }

    @Override
    public List<CourseStudentVO> getCoursesByStuId(Integer id) {
        List<CourseStudentDO> courseStudentDOs = courseStudentDOMapper.selectByStuId(id);
        List<CourseStudentVO> courseStudentVOS = new ArrayList<>();
        for (CourseStudentDO courseStudentDO : courseStudentDOs) {
            CourseStudentVO courseStudentVO = new CourseStudentVO();
            BeanUtils.copyProperties(courseStudentDO, courseStudentVO);
            if (courseStudentDO.getCourseId() == 0) {
                continue;
            }
            courseStudentVO.setCourseName(getCourseNameById(courseStudentDO.getCourseId()));
            courseStudentVOS.add(courseStudentVO);
        }
        return courseStudentVOS;
    }

    @Override
    public void add(CourseStudentDO courseStudentDO) {
        courseStudentDO.setCreatedAt(new Date());
        courseStudentDO.setUpdatedAt(new Date());
        courseStudentDOMapper.insertSelective(courseStudentDO);
    }

    @Override
    public void update(CourseStudentDO courseStudentDO) {
        courseStudentDO.setUpdatedAt(new Date());
        courseStudentDOMapper.updateByPrimaryKeySelective(courseStudentDO);
    }

    @Override
    public void deleteCourseById(Integer id) {
        courseStudentDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, List<StatisticsDO>> getStatistics() {
        Map<String, List<StatisticsDO>> statisticsMap = new HashMap<>();
        statisticsMap.put("dayData", courseStudentDOMapper.selectDayData());
        statisticsMap.put("weekData", courseStudentDOMapper.selectWeekData());
        statisticsMap.put("monthData", courseStudentDOMapper.selectMonthData());
        statisticsMap.put("yearData", courseStudentDOMapper.selectYearData());
        return statisticsMap;
    }

    @Override
    public Map<String, GraphDO> getGraph() {
        Map<String, GraphDO> graphMap = new HashMap<>();

        GraphDO incomeGraph = new GraphDO();
        GraphDO countGraph = new GraphDO();

        List<String> weeksToNow = new ArrayList<>();
        int currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        for (int i = 1; i <= currentWeek; i++) {
            weeksToNow.add("第"+i+"周");
        }
        incomeGraph.setLabels(weeksToNow);
        countGraph.setLabels(weeksToNow);

        String label1 = "收入";
        List<Integer> dataList1 = new ArrayList<>(currentWeek);
        for (int i = 0; i < currentWeek; i++) {
            dataList1.add(0);
        }
        String label2 = "减免";
        List<Integer> dataList2 = new ArrayList<>(currentWeek);
        for (int i = 0; i < currentWeek; i++) {
            dataList2.add(0);
        }
        String label3 = "退费";
        List<Integer> dataList3 = new ArrayList<>(currentWeek);
        for (int i = 0; i < currentWeek; i++) {
            dataList3.add(0);
        }
        String label4 = "报名人数";
        List<Integer> dataList4 = new ArrayList<>(currentWeek);
        for (int i = 0; i < currentWeek; i++) {
            dataList4.add(0);
        }

        List<StatisticsDO> statisticsDOS = courseStudentDOMapper.selectWeekData();
        for (StatisticsDO statisticsDO : statisticsDOS) {
            Integer index = Integer.parseInt(statisticsDO.getPeriod().substring(5)) - 1;
            dataList1.set(index, statisticsDO.getIncome());
            dataList2.set(index, statisticsDO.getDiscount());
            dataList3.set(index, statisticsDO.getRefund());
            dataList4.set(index, statisticsDO.getStuCount());
        }

        Map<String, Object> dataset1 = new HashMap<>();
        dataset1.put("label", label1);
        dataset1.put("data", dataList1);
        Map<String, Object> dataset2 = new HashMap<>();
        dataset2.put("label", label2);
        dataset2.put("data", dataList2);
        Map<String, Object> dataset3 = new HashMap<>();
        dataset3.put("label", label3);
        dataset3.put("data", dataList3);

        Map<String, Object> dataset4 = new HashMap<>();
        dataset4.put("label", label4);
        dataset4.put("data", dataList4);

        List<Map<String, Object>> incomeDatasets = new ArrayList<>();
        incomeDatasets.add(dataset1);
        incomeDatasets.add(dataset2);
        incomeDatasets.add(dataset3);

        List<Map<String, Object>> countDatasets = new ArrayList<>();
        countDatasets.add(dataset4);

        incomeGraph.setDatasets(incomeDatasets);
        countGraph.setDatasets(countDatasets);

        graphMap.put("income", incomeGraph);
        graphMap.put("stuCount", countGraph);

        return graphMap;
    }

    @Override
    public List<CourseStudentVO> getCoursesByStuAndSignUpId(Integer studentId, Integer signupId) {
        List<CourseStudentDO> courseStudentDOs = courseStudentDOMapper.selectByStuIdAndSignupId(studentId, signupId);
        List<CourseStudentVO> courseStudentVOS = new ArrayList<>();
        for (CourseStudentDO courseStudentDO : courseStudentDOs) {
            if (courseStudentDO.getCourseId() == 0) {
                continue;
            }
            CourseStudentVO courseStudentVO = new CourseStudentVO();
            BeanUtils.copyProperties(courseStudentDO, courseStudentVO);
            courseStudentVO.setCourseName(getCourseNameById(courseStudentDO.getCourseId()));
            courseStudentVOS.add(courseStudentVO);
        }
        return courseStudentVOS;
    }

    private String getCourseNameById(Integer courseId) {
        CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
        String courseName = courseDO.getTerm() + "/" + courseDO.getType() + "/" + courseDO.getGrade() + "/" + courseDO.getSubject();
        return courseName;
    }

}
