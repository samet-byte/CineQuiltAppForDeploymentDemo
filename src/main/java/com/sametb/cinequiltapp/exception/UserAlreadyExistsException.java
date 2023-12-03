package com.sametb.cinequiltapp.exception;

/**
 * @author Samet Bayat.
 * Date: 3.11.2023 10:18 PM
 * Project Name: bil387-springboot-react-demo
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){ super(message); }



}
