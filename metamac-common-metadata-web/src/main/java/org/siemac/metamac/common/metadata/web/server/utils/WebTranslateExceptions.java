package org.siemac.metamac.common.metadata.web.server.utils;

import java.util.Locale;

import org.siemac.metamac.core.common.exception.utils.TranslateExceptions;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.springframework.stereotype.Component;

@Component
public class WebTranslateExceptions extends TranslateExceptions {

    public String getTranslatedMessage(String code, String localString) {
        Locale locale = LocaleUtil.getLocaleFromLocaleString(localString);
        return translateMessage(code, locale);
    }

    protected String translateMessage(String code, Locale locale) {
        return LocaleUtil.getLocalizedMessageFromBundle("i18n/messages-common_metadata-web", code, locale);
    }
}
