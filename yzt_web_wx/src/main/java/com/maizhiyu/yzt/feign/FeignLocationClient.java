package com.maizhiyu.yzt.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "location", url = "${weixin.location_domain}")
public interface FeignLocationClient {

    @GetMapping(value = "${weixin.location_path}?key=${weixin.location_key}")
    String getLocation(
            @RequestParam("location") String location);

}
