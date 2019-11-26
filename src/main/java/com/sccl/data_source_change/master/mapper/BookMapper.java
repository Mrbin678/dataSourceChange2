package com.sccl.data_source_change.master.mapper;



import com.sccl.data_source_change.master.domain.Book;

import java.util.List;

/**
 * Create by wangbin
 * 2019-08-07-1:18
 */
public interface BookMapper {
    List<Book> getAllBooks();
    int addBook(Book book);
}
