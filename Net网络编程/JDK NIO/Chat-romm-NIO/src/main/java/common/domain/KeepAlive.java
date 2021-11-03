package common.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName KeepAlive
 * @Description 心跳包对象
 * @Author Lijuce_K
 * @Date 2021/10/30 0030 10:24
 * @Version 1.0
 **/
public class KeepAlive extends Message implements Serializable {
    private static final long serialVersionUID = -2813120366138988480L;

    @Override
    public String toString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t维持链接包";
    }
}

