package com.retail.admin.networking;

import base.model.monitor.AdminStatus;
import feign.Param;
import feign.RequestLine;

public interface RetailExperienceAdmin {

    @RequestLine("GET /login/{username}/{password}")
    boolean login(@Param("username") String username, @Param("password") String password);

    @RequestLine("GET /status")
    AdminStatus getStatus();
}
