package com.ge.trans.rmd.common.exception;

public class OMDDirectoryCreationException extends OMDApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * @param errorCode
     * @param strErrMsg
     */
    public OMDDirectoryCreationException(final String errorCode, final String strErrMsg) {
        super(errorCode, strErrMsg);
    }

    public OMDDirectoryCreationException(String errorCode) {
        super(errorCode);
    }

}
