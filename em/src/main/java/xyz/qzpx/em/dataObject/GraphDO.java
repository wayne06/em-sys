package xyz.qzpx.em.dataObject;

import java.util.List;
import java.util.Map;

public class GraphDO {

    private List<String> labels;

    private List<Map<String, Object>> datasets;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Map<String, Object>> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Map<String, Object>> datasets) {
        this.datasets = datasets;
    }
}
