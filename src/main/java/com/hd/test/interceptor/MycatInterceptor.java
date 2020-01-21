package com.hd.test.interceptor;

public class MycatInterceptor{

    private static final ThreadLocal<String> SCHEMA_LOCAL = new ThreadLocal<>();

    private static final String SCHEMA_SCRIPT_START = "/*!mycat:schema = ";

    private static final String SCHEMA_SCRIPT_END = " */";


}
