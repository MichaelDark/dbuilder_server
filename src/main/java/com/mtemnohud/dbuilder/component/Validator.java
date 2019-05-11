package com.mtemnohud.dbuilder.component;

import com.mtemnohud.dbuilder.exception.BadRequestException;
import com.mtemnohud.dbuilder.config.MessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@SuppressWarnings("unchecked")
public class Validator {

    private final HttpServletRequest request;

    private final MessageConfig messageConfig;

    private Predicate nullCheckPredicate;


    @Autowired
    public Validator(HttpServletRequest request, MessageConfig messageConfig) {
        this.request = request;
        this.messageConfig = messageConfig;
        initPredicate();
    }

    private void initPredicate() {
        nullCheckPredicate = Objects::isNull;
    }

    public Validator boolValidate(boolean expression, String messagePath) {
        if (expression) {
            throw new BadRequestException(messageConfig.messageSource().getMessage(messagePath, null, messageConfig.localeResolver().resolveLocale(request)));
        }
        return this;
    }

    public Validator boolValidate(boolean expression, String messagePath, Object... messageItems) {
        if (expression) {
            throw new BadRequestException(messageConfig.messageSource().getMessage(messagePath, messageItems, messageConfig.localeResolver().resolveLocale(request)));
        }
        return this;
    }

    public Validator boolValidate(boolean expression, String messagePath, Class<? extends RuntimeException> exceptionClazz) {
        if (expression) {
            throw (exceptionClazz.cast(new RuntimeException(messageConfig.messageSource().getMessage(messagePath, null, messageConfig.localeResolver().resolveLocale(request)))));
        }
        return this;
    }

    public Validator boolValidate(boolean expression, String messagePath, Class<? extends RuntimeException> exceptionClazz, Object... messageItems) {
        if (expression) {
            throw (exceptionClazz.cast(new RuntimeException(messageConfig.messageSource().getMessage(messagePath, messageItems, messageConfig.localeResolver().resolveLocale(request)))));
        }
        return this;
    }

    public Validator nullValidate(Object object, String messagePath) {
        if (nullCheckPredicate.test(object)) {
            throw new BadRequestException(messageConfig.messageSource().getMessage(messagePath, null, messageConfig.localeResolver().resolveLocale(request)));
        }
        return this;
    }

    public Validator nullValidate(Object object, String messagePath, Object... messageItems) {
        if (nullCheckPredicate.test(object)) {
            throw new BadRequestException(messageConfig.messageSource().getMessage(messagePath, messageItems, messageConfig.localeResolver().resolveLocale(request)));
        }
        return this;
    }

    public Validator nullValidate(Object object, String messagePath, Class<? extends RuntimeException> exceptionClazz) {
        if (nullCheckPredicate.test(object)) {
            throw (exceptionClazz.cast(new RuntimeException(messageConfig.messageSource().getMessage(messagePath, null, messageConfig.localeResolver().resolveLocale(request)))));
        }
        return this;
    }

    public Validator nullValidate(Object object, String messagePath, Class<? extends RuntimeException> exceptionClazz, Object... messageItems) {
        if (nullCheckPredicate.test(object)) {
            throw (exceptionClazz.cast(new RuntimeException(messageConfig.messageSource().getMessage(messagePath, messageItems, messageConfig.localeResolver().resolveLocale(request)))));
        }
        return this;
    }
}