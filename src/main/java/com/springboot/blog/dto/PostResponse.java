package com.springboot.blog.dto;

import java.util.List;

public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pagesSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public List<PostDto> getContent() {
        return content;
    }

    public void setContent(List<PostDto> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getPagesSize() {
        return pagesSize;
    }

    public void setPagesSize(int pagesSize) {
        this.pagesSize = pagesSize;
    }
}
