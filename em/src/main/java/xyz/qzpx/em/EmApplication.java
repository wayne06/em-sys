package xyz.qzpx.em;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qzpx.em.dao.StudentDOMapper;
import xyz.qzpx.em.dataObject.StudentDO;

@SpringBootApplication(scanBasePackages = {"xyz.qzpx.em"})
@MapperScan("xyz.qzpx.em.dao")
@RestController
public class EmApplication {

    @Autowired
    private StudentDOMapper studentDOMapper;

    public static void main(String[] args) {
        SpringApplication.run(EmApplication.class, args);
    }

    @RequestMapping("/")
    public String homeTest() {
        StudentDO studentDO = studentDOMapper.selectByPrimaryKey(1);
        if (studentDO == null) {
            return "student not exists";
        } else {
            return studentDO.toString();
        }
    }

}
