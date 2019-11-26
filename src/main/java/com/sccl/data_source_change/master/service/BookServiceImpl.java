package com.sccl.data_source_change.master.service;


import com.sccl.data_source_change.aspectj.annotation.DataSource;
import com.sccl.data_source_change.enumConst.DataSourceEnum;
import com.sccl.data_source_change.master.domain.Book;
import com.sccl.data_source_change.master.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by wangbin
 * 2019-11-18-17:57
 */
@Transactional
@Service
public class BookServiceImpl  implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @DataSource(value = DataSourceEnum.MASTER)
    @Override
    public List<Book> getAllBooks() {
        return bookMapper.getAllBooks();
    }
    @DataSource(value = DataSourceEnum.SLAVE)
    @Override
    public List<Book> getAllBooks2() {
        return bookMapper.getAllBooks();
    }
    @DataSource(value = DataSourceEnum.MASTER)
    @Override
    public int addBook(Book book) {
        return bookMapper.addBook(book);
    }
    @DataSource(value = DataSourceEnum.SLAVE)
    @Override
    public int addBook2(Book book) {
        return bookMapper.addBook(book);
    }
}
