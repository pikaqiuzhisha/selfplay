import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import javax.annotation.Resource;

public class PageHelper {
    @Resource
    private ParkInfoService parkInfoService;
    @Test
    public void contextLoads() {
        Page<PageInfo> page =parkInfoService.getAll(1,2);
        PageInfo<PageInfo> pageInfo = new PageInfo<PageInfo>(page);
//        String data = JSON.toJSONString(pageInfo);
//        System.out.println(data);
    }
}
