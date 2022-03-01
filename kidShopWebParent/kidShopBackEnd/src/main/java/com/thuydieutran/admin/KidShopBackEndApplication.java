package com.thuydieutran.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.thuydieutran.kidshopcommon.entities", "com.thuydieutran.admin.user"})
public class KidShopBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(KidShopBackEndApplication.class, args);
    }

}
