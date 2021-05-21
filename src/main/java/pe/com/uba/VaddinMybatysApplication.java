package pe.com.uba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
//@MapperScan(basePackages = "pe.com.uba.dao")
@EnableTransactionManagement(proxyTargetClass=true)
public class VaddinMybatysApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaddinMybatysApplication.class, args);
	}

}
