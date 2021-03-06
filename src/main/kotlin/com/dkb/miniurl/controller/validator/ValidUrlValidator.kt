package com.dkb.miniurl.controller.validator

import com.dkb.miniurl.business.services.UrlShortenerService
import org.hibernate.annotations.common.util.impl.LoggerFactory
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * The class validates the format of URL send in the request
 */
class ValidUrlValidator : ConstraintValidator<ValidUrl, String> {

    val logger = LoggerFactory.logger(ValidUrlValidator::class.java)

    override fun isValid(url: String?, context: ConstraintValidatorContext): Boolean {
        context.disableDefaultConstraintViolation()
        val valid = validateUrl(url)
        return if (!valid) {
            logger.error("Url ${url} passed in the request is not of correct ")
            context.buildConstraintViolationWithTemplate("Url passed in the request is not of correct ")
                .addConstraintViolation()
            return false
        } else true
    }

    private fun validateUrl(url: String?): Boolean {
        val regex =
            "((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.[a-zA-Z]+"

        var pattern = Pattern.compile(regex)
        return pattern.matcher(url).matches()
    }

}