package ru.rsreu.projectmanagment.identityservice.identityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IdentityServiceApplication {

    //TODO Рейтинг
    //TODO Отклик на ванаксию доделать, очень странно, что смотрятся все отклики без приаязки к вакансиям
    //TODO Уведомления
    //TODO Связать зависисые элементы
    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

}
