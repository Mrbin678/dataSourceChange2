package com.sccl.data_source_change;

import com.sccl.data_source_change.config.DruidMutilConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Import(DruidMutilConfig.class)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DataSourceChangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceChangeApplication.class, args);
    }

}
