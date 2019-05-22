package com.gehad.todotask.common.util;

import java.util.Random;

public class CommonUtils {
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }}
