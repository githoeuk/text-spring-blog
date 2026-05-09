package com.hewak.blog.util;

import org.apache.commons.lang3.time.DateFormatUtils;


import lombok.Data;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

public class MyDataUtil {

    // 1. TimeStamp 포메터
    public static String timestampFormat(Timestamp timestamp) {
        // TimeStamp --> Date 형태로 변환
        Date currentDate = new Date(timestamp.getTime());
        return DateFormatUtils.format(currentDate, "yyyy-MM-dd HH:mm");
    }


}
