
/*该类不是用于测试服务器的，只是我个人的一些尝试而已*/
package net.xufang.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//该类不是用于测试服务器，不是用于测试服务器，不是用于测试服务器，只是为了实现某些功能的一些小尝试而已
public class Test {
    public static void main(String[] args) {
        File tmpfile;
        tmpfile = new File("src/net/xufang/resource/Sophon.html");
        Calendar cal = Calendar.getInstance();
        long time = tmpfile.lastModified();
        cal.setTimeInMillis(time);
        SimpleDateFormat sf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.US);
        String sftime = sf.format(cal.getTime());
        sftime = sftime + " GMT";
        System.out.println(sftime);
    }
}
