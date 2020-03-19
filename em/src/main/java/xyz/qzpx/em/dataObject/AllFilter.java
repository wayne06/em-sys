package xyz.qzpx.em.dataObject;

import java.util.List;

public class AllFilter {

    private List<FiltersItem> term;
    private List<FiltersItem> type;
    private List<FiltersItem> grade;
    private List<FiltersItem> subject;

    public List<FiltersItem> getTerm() {
        return term;
    }

    public void setTerm(List<FiltersItem> term) {
        this.term = term;
    }

    public List<FiltersItem> getType() {
        return type;
    }

    public void setType(List<FiltersItem> type) {
        this.type = type;
    }

    public List<FiltersItem> getGrade() {
        return grade;
    }

    public void setGrade(List<FiltersItem> grade) {
        this.grade = grade;
    }

    public List<FiltersItem> getSubject() {
        return subject;
    }

    public void setSubject(List<FiltersItem> subject) {
        this.subject = subject;
    }
}
