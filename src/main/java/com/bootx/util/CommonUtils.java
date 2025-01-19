package com.bootx.util;

public class CommonUtils {

    public static String getOperatingSystem(String userAgent) {
        if (userAgent == null) {
            return "Unknown";
        }

        // 检查操作系统类型
        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Macintosh")) {
            return "Mac OS";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iPhone")) {
            return "iOS";
        } else if (userAgent.contains("iPad")) {
            return "iOS";
        }

        return "Unknown";
    }

    public static String getBrowser(String secChUa) {
        if (secChUa == null || secChUa.isEmpty()) {
            return "Unknown Browser";
        }
        // 去掉双引号并分割字符串
        String[] parts = secChUa.replace("\"", "").split(", ");
        for (String part : parts) {
            System.out.println(part);
            // 查找包含浏览器名称的部分
            if (part.contains("Microsoft Edge") ||part.contains("Google Chrome") || part.contains("Chromium") || part.contains("Firefox")) {
                return part.split(";")[0]+" "+part.split(";")[1];
            }
        }

        return "Unknown Browser";
    }

}
