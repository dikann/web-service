package com.dikann.webservice.utils;

public class ApplicationConst {
    public final static String baseUrl = "/dk/api/v1/";
    public final static String pageNo = "0";
    public final static String pageSize = "10";
    public final static String sortBy = "id";
    public final static String sortDir = "asc";

    public final static String errorObjectNotFoundCode = "OBJECT_NOT_FOUND";
    public final static String errorObjectNotFoundMessage = "The Object was not found. It was either deleted or doesn't exist!";

    public static final String AUTHORITIES_KEY = "roles";

    public static String errorObjectWithNameNotFoundMessage(String name) {
        return String.format("The %s was not found. It was either deleted or doesn't exist!", name);
    }
}