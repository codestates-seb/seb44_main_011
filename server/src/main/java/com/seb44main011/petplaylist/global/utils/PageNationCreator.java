package com.seb44main011.petplaylist.global.utils;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

public  class PageNationCreator {
    public static final int ORIGIN_PAGE_SIZE_OF_SIX = 6;
    public static Pageable getPageOfDesc(int page,int pageSize,String ColumnToSort){
        return PageRequest.of(page-1, pageSize, Sort.by(ColumnToSort).descending());

    }
    public static Pageable getPageOfDesc(int page,int pageSize){
        return PageRequest.of(page-1, pageSize);

    }
    public static Pageable getPageOfASC(int page,int pageSize,String ColumnToSort){
        return PageRequest.of(page-1, pageSize, Sort.by(ColumnToSort).ascending());

    }
    public static Pageable getPageOfASC(int page,int pageSize){
        return PageRequest.of(page-1, pageSize);

    }
    public static <T> Page<T> createPage(List<T> pageContentList, Pageable pageable){
        int contentSize = pageContentList.size();
        int initPage = (pageable.getPageNumber()+1)*pageable.getPageSize();
        int cutPage = pageable.getPageSize()*pageable.getPageNumber();

        if (contentSize <cutPage){
            return new PageImpl<>(new ArrayList<>(),pageable,pageContentList.size());
        }
        if (contentSize >=cutPage+6){
            return new PageImpl<>(pageContentList.subList(cutPage, cutPage+6),pageable, contentSize);
        }

        return new PageImpl<>(pageContentList.subList(cutPage, pageContentList.size()),pageable, contentSize);
    }

}
