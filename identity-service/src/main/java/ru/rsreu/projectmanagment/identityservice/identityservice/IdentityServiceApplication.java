package ru.rsreu.projectmanagment.identityservice.identityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdentityServiceApplication {

    //TODO Отзывы о компаниях
    //TODO Рейтинг
    //TODO Отклик на ванаксию доделать, очень странно, что смотрятся все отклики без приаязки к вакансиям
    //TODO При обновлении профиля не создаются несуществующие сущности, а выкидывается ошибка
    //TODO Уведомления
    //TODO Файлы в 3S
    //TODO Связать зависисые элементы
    //TODO В создании ваканчсии не устанавливаются спициальности для вакансии при создании
    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

}
