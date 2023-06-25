package com.example.security.context.services;

import java.util.concurrent.Callable;

public interface AsyncService {

    void asyncCall();

    Callable<Boolean> checkIfPrincipalPropagated();
}
