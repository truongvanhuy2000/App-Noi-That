package com.huy.appnoithat.Exception;

import com.huy.appnoithat.Common.PopupUtils;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@NoArgsConstructor
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    final static Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        PopupUtils.throwErrorNotification("Lỗi hệ thống, vui lòng thông báo cho nhà phát triển");
        LOGGER.log(Level.INFO, throwable.getMessage(), throwable);
    }
}
