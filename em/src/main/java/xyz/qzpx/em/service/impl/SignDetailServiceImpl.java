package xyz.qzpx.em.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.qzpx.em.dao.CourseDOMapper;
import xyz.qzpx.em.dao.SignDetailDOMapper;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.*;
import xyz.qzpx.em.service.SignDetailService;

import java.util.*;

@Service
public class SignDetailServiceImpl implements SignDetailService {

    @Autowired
    private StudentDOMapper studentDOMapper;

    @Autowired
    private CourseDOMapper courseDOMapper;

    @Autowired
    private SignDetailDOMapper signDetailDOMapper;

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
        //CourseStudentDO courseStudentDO = new CourseStudentDO();
        //courseStudentDO.setCourseId(courseId);
        //courseStudentDO.setStudentId(stuId);
        //courseStudentDO.setCreatedAt(new Date());
        //courseStudentDO.setUpdatedAt(new Date());
        //courseStudentDOMapper.insert(courseStudentDO);
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
    public List<SignDetailDO> getCoursesByStuId(Integer id) {
        List<SignDetailDO> signDetailDOS = signDetailDOMapper.selectByStuId(id);
        Iterator<SignDetailDO> iterator = signDetailDOS.iterator();
        while (iterator.hasNext() && "0".equals(iterator.next().getCourses())) {
            iterator.remove();
        }
        return signDetailDOS;
    }

    @Override
    public Integer add(SignDetailDO signDetailDO) {
        signDetailDO.setCreatedAt(new Date());
        signDetailDO.setUpdatedAt(new Date());
        signDetailDOMapper.insertSelective(signDetailDO);
        System.out.println(signDetailDO.getId());
        return signDetailDO.getId();
    }

    @Override
    public void update(SignDetailDO signDetailDO) {
        signDetailDO.setUpdatedAt(new Date());
        signDetailDOMapper.updateByPrimaryKeySelective(signDetailDO);
    }

    @Override
    public void deleteCourseById(Integer id) {
        signDetailDOMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, List<StatisticsDO>> getStatistics() {
        Map<String, List<StatisticsDO>> statisticsMap = new HashMap<>();
        statisticsMap.put("dayData", signDetailDOMapper.selectDayData());
        statisticsMap.put("weekData", signDetailDOMapper.selectWeekData());
        statisticsMap.put("monthData", signDetailDOMapper.selectMonthData());
        statisticsMap.put("yearData", signDetailDOMapper.selectYearData());
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

        List<StatisticsDO> statisticsDOS = signDetailDOMapper.selectWeekData();
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
    public List<SignDetailDO> getCoursesByStuAndSignUpId(Integer studentId, Integer signupId) {
        List<SignDetailDO> signDetailDOS = signDetailDOMapper.selectByStuIdAndSignupId(studentId, signupId);
        Iterator<SignDetailDO> iterator = signDetailDOS.iterator();
        while (iterator.hasNext() && "0".equals(iterator.next().getCourses())) {
            iterator.remove();
        }
        for (SignDetailDO signDetailDO : signDetailDOS) {
            signDetailDO.setCourses(signDetailDO.getCourses().replaceAll("\\:[0-9]*\\;", "<br/>"));
        }
        return signDetailDOS;
    }

    @Override
    public SignDetailDO getById(Integer id) {
        return signDetailDOMapper.selectByPrimaryKey(id);
    }

    //private String getCourseNameById(Integer courseId) {
    //    CourseDO courseDO = courseDOMapper.selectByPrimaryKey(courseId);
    //    String courseName = courseDO.getTerm() + "/" + courseDO.getType() + "/" + courseDO.getGrade() + "/" + courseDO.getSubject();
    //    return courseName;
    //}
}
