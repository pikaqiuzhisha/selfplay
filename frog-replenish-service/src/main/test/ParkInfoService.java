import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public interface ParkInfoService {
    Page<PageInfo> getAll(int i, int i1);
}
