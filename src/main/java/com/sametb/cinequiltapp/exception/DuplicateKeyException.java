package com.sametb.cinequiltapp.exception;

/**
 * @author Samet Bayat.
 * Date: 28.11.2023 6:29 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(String message) {
        super(message);
    }
}

