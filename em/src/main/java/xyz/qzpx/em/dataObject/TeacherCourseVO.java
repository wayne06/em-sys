package xyz.qzpx.em.dataObject;

import java.util.Date;

public class TeacherCourseVO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.created_at
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private Date createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.updated_at
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private Date updatedAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.signup_id
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    private Integer signupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.teacher_id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private Integer teacherId;

    private String teacherName;

    private String telephone;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.course_id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private Integer courseId;

    private String courseName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.lesson_count
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private Integer lessonCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.period
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private String period;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column teacher_course.remark
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.id
     *
     * @return the value of teacher_course.id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.id
     *
     * @param id the value for teacher_course.id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.created_at
     *
     * @return the value of teacher_course.created_at
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.created_at
     *
     * @param createdAt the value for teacher_course.created_at
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.updated_at
     *
     * @return the value of teacher_course.updated_at
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.updated_at
     *
     * @param updatedAt the value for teacher_course.updated_at
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.signup_id
     *
     * @return the value of teacher_course.signup_id
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    public Integer getSignupId() {
        return signupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.signup_id
     *
     * @param signupId the value for teacher_course.signup_id
     *
     * @mbg.generated Thu Apr 09 11:10:46 CST 2020
     */
    public void setSignupId(Integer signupId) {
        this.signupId = signupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.teacher_id
     *
     * @return the value of teacher_course.teacher_id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.teacher_id
     *
     * @param teacherId the value for teacher_course.teacher_id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.course_id
     *
     * @return the value of teacher_course.course_id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.course_id
     *
     * @param courseId the value for teacher_course.course_id
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.lesson_count
     *
     * @return the value of teacher_course.lesson_count
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public Integer getLessonCount() {
        return lessonCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.lesson_count
     *
     * @param lessonCount the value for teacher_course.lesson_count
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setLessonCount(Integer lessonCount) {
        this.lessonCount = lessonCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.period
     *
     * @return the value of teacher_course.period
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public String getPeriod() {
        return period;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.period
     *
     * @param period the value for teacher_course.period
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column teacher_course.remark
     *
     * @return the value of teacher_course.remark
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column teacher_course.remark
     *
     * @param remark the value for teacher_course.remark
     *
     * @mbg.generated Fri Mar 20 22:38:13 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
