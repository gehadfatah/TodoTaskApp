package com.gehad.todotask.common.util;

public class InterfaceNotImplementedException extends RuntimeException {

    public InterfaceNotImplementedException(Exception e, String classNameThatShouldImplement, String interfaceNameToImplement) {
        super(classNameThatShouldImplement + " must implement " + interfaceNameToImplement, e);
    }
}