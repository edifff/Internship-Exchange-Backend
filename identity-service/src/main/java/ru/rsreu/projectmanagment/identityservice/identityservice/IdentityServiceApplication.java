package ru.rsreu.projectmanagment.identityservice.identityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableAsync
@EnableScheduling
public class IdentityServiceApplication {

    //TODO Рейтинг
    //TODO Отклик на ванаксию доделать, очень странно, что смотрятся все отклики без приаязки к вакансиям
    //TODO Уведомления
    //TODO Связать зависисые элементы
    //TODO Зарегистрироваться как админ можно если в запросе внести роль админа
    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

}
