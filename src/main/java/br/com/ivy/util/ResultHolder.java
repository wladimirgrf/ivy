package br.com.ivy.util;

import java.util.Collection;

public class ResultHolder<T> {
	
	private Collection<T> result;
    private int resultSize;
    private int timeElapsed;
    private int page;
    private int pageSize;

    public Collection<T> getResult() {
        return result;
    }

    public void setResult(Collection<T> result) {
        this.result = result;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}