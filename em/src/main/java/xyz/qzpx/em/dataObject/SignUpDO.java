package xyz.qzpx.em.dataObject;

public class SignUpDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sign_up.id
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sign_up.title
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sign_up.username
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sign_up.status
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sign_up.timeline
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    private String timeline;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sign_up.id
     *
     * @return the value of sign_up.id
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sign_up.id
     *
     * @param id the value for sign_up.id
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sign_up.title
     *
     * @return the value of sign_up.title
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sign_up.title
     *
     * @param title the value for sign_up.title
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sign_up.username
     *
     * @return the value of sign_up.username
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sign_up.username
     *
     * @param username the value for sign_up.username
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sign_up.status
     *
     * @return the value of sign_up.status
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sign_up.status
     *
     * @param status the value for sign_up.status
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sign_up.timeline
     *
     * @return the value of sign_up.timeline
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public String getTimeline() {
        return timeline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sign_up.timeline
     *
     * @param timeline the value for sign_up.timeline
     *
     * @mbg.generated Sun Apr 19 21:23:39 CST 2020
     */
    public void setTimeline(String timeline) {
        this.timeline = timeline == null ? null : timeline.trim();
    }
}
