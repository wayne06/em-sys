package xyz.qzpx.em.dataObject;

import java.util.Date;

public class StudentDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.id
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.created_at
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private Date createdAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.updated_at
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private Date updatedAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.name
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.gender
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String gender;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.school
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String school;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.grade_and_class
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String gradeAndClass;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.parent_name
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String parentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.telephone
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String telephone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.address
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student.remark
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.id
     *
     * @return the value of student.id
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.id
     *
     * @param id the value for student.id
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.created_at
     *
     * @return the value of student.created_at
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.created_at
     *
     * @param createdAt the value for student.created_at
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.updated_at
     *
     * @return the value of student.updated_at
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.updated_at
     *
     * @param updatedAt the value for student.updated_at
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.name
     *
     * @return the value of student.name
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.name
     *
     * @param name the value for student.name
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.gender
     *
     * @return the value of student.gender
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getGender() {
        return gender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.gender
     *
     * @param gender the value for student.gender
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.school
     *
     * @return the value of student.school
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getSchool() {
        return school;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.school
     *
     * @param school the value for student.school
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.grade_and_class
     *
     * @return the value of student.grade_and_class
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getGradeAndClass() {
        return gradeAndClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.grade_and_class
     *
     * @param gradeAndClass the value for student.grade_and_class
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setGradeAndClass(String gradeAndClass) {
        this.gradeAndClass = gradeAndClass == null ? null : gradeAndClass.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.parent_name
     *
     * @return the value of student.parent_name
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.parent_name
     *
     * @param parentName the value for student.parent_name
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.telephone
     *
     * @return the value of student.telephone
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.telephone
     *
     * @param telephone the value for student.telephone
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.address
     *
     * @return the value of student.address
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.address
     *
     * @param address the value for student.address
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column student.remark
     *
     * @return the value of student.remark
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column student.remark
     *
     * @param remark the value for student.remark
     *
     * @mbg.generated Thu Mar 19 10:19:23 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}