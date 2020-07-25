import com.yyc.dao.UserMapper;
import com.yyc.entity.example.UserExample;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-web.xml",
        "classpath:spring/spring-base.xml"})
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectUser() {
        System.out.println(userMapper.selectByPrimaryKey("201050019"));
    }

    @Test
    public void generatePwd() {
        String str = "166322";

        String salt = "201050019";

        String res = new SimpleHash("MD5", str, salt).toString();

        System.out.println(res);
    }
}
