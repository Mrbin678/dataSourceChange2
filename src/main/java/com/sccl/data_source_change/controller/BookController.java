package com.sccl.data_source_change.controller;


import com.sccl.data_source_change.master.domain.Book;
import com.sccl.data_source_change.master.service.BookService;
import com.sccl.data_source_change.slave.domain.User;
import com.sccl.data_source_change.slave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**在controller层中注入不同的mapper实例，操作不同的数据源
 * Create by wangbin
 * 2019-08-07-1:26
 */
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/test1")//测试查询主从库的数据
    public String test1(){
        List<Book> books1 = bookService.getAllBooks();
        List<Book> books2 = bookService.getAllBooks2();
        System.out.println("books1:"+books1);
        System.out.println("books2:"+books2);
        List<User> users1 = userService.getAllUsers();
        System.out.println("user1:"+users1);
        return "OK";
    }
    @Transactional
    @GetMapping("/test2")//测试主从双库写入
    public String test2(){
        Book book = new Book();
        book.setName("罗宾逊");
        book.setAuthor("漂流记");
        int bookNumber = bookService.addBook(book);
        Book book2 = new Book();
        book2.setName("飞驰人生");
        book2.setAuthor("韩寒");
        int bookNumber2 = bookService.addBook2(book2);
        System.out.println("向master数据库添加数据:"+bookNumber);
        System.out.println("向slave数据库添加数据:"+bookNumber2);
        int number = 1/0;//自定义错误，查看事务是否回滚
        return "OK";
    }
    @Transactional
    @GetMapping("/test3")
    public String test3(){
        Book book = new Book();
        book.setName("master");
        book.setAuthor("master");
        int bookNumber = bookService.addBook(book);
        User user = new User();
        user.setAge(18);
        user.setGender("男");
        user.setName("slave");
        int userNumber = userService.addUser(user);
        //int number = 1/0;
        return "OK";
    }
}
