package xyz.qzpx.em.dataObject;

import java.util.List;

public class TreeItem {

    private String value;

    private String label;

    private List<TreeItem> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeItem> getChildren() {
        return children;
    }

    public void setChildren(List<TreeItem> children) {
        this.children = children;
    }
}
