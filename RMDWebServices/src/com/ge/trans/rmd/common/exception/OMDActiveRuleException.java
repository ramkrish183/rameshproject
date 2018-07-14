package com.ge.trans.rmd.common.exception;

public class OMDActiveRuleException extends OMDApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * @param strErrorCode
     * @param strErrorMessage
     */

    public OMDActiveRuleException(String strErrorCode, String strErrorMessage, String strErrorType) {
        super(strErrorCode, strErrorMessage, strErrorType);
    }

    public OMDActiveRuleException(String strErrorMessage) {
        super(strErrorMessage);
    }

}
