package com.gjf.lovezzu.entity.school;

import java.util.List;

/**
 * Created by BlackBeard丶 on 2017/03/23.
 */
public class SchoolNewsData {
    private boolean error;
    private List<SchoolNewsResult> results;

    public List<SchoolNewsResult> getResults() {
        return results;
    }

    public void setResults(List<SchoolNewsResult> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
