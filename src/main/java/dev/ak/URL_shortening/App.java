package dev.ak.URL_shortening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	//TODO Добавить глобальный обработчик ошибок
	//TODO Добавить логгирование
	//TODO Добавить @Async для увеличения счетчика просмотров

}
