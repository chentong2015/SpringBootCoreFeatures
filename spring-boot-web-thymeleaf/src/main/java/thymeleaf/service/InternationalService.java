package thymeleaf.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class InternationalService {

    private final MessageSource messageSource;

    public InternationalService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // 找到属性定义的模板信息并替换相关参数
    // gameMessage=Number is between {0} and {1}, can you guess it
    public String getGameMessage() {
        String key = "gameMessage";
        int smallestNum = 1;
        int biggestNum = 100;
        return getPropertyMessage(key, smallestNum, biggestNum);
    }

    // getLocale()获取当前使用的语言
    private String getPropertyMessage(String key, Object... args) {
        // LocaleContextHolder is the central for the current locale in Spring
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}










