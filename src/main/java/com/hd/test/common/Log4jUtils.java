package com.hd.test.common;

import java.io.FileNotFoundException;

public class Log4jUtils {

    private static final String OS_NAME = "Mac OS X";

    private static final String MAC_LOG_NAME = "/Users/houdu/usr/properties/testlog/log4j2_mac.xml";

    private static final String WIN_LOG_NAME = "/usr/properties/testlog/log4j2_windows.xml";

    /**
     * 加载项目根目录下的log4j2.xml文件
     * 应用程序打包后，xml可以与Jar包放到同一个目录下，方便用户修改日志等级
     */
    public static String setLogConfigFilePath() throws FileNotFoundException {
        //method2 System.getProperty
        String osName = System.getProperty("os.name");
//        String fullPath = config + "\\log4j2.xml";
        String fullPath = StringUtils.isEquals(OS_NAME,osName) ? MAC_LOG_NAME : WIN_LOG_NAME;

        //这种方式在spring boot不可用
//        ConfigurationSource source;
//        File file = new File(fullPath);
//        if (file.exists()) {
//            //this ConfigurationSource() could load xml dynamicly, info->debug->info test OK!!!
//            source = new ConfigurationSource(new FileInputStream(file), file);
//            Configurator.initialize(null, source);
////            logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
//        }

        System.setProperty("logging.config", fullPath);
        return fullPath;
    }

}