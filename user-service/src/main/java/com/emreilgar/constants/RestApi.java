package com.emreilgar.constants;

public class RestApi {
    public static final String VERSION = "api/v1";
    public static final String USER = VERSION+"/user";
    public static final String CREATE  ="/create";

    public static final String UPDATE  ="/update";
    public static final String UPDATE2  ="/update2";
    public static final String UPDATEBYUSERNAMEOREMAIL  ="/updateusernameoremail";
    public static final String GETBYROLE  ="/getbyrole/{role}";
    public static final String FINDALL  ="/findall";
    public static final String FINDBYID  ="/findbyid";
    public static final String DELETEBYID ="/deletebyid";
    public static final String DELETEBYAUTHID ="/deletebyauthid";
    public static final String ACTIVATESTATUS= "/activatestatus";

    public static final String ACTIVATESTATUSBYID= "/activatestatus/{authid}";
}
