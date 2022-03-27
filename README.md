#### Приложение - клиентская часть микросервиса для обмена сообщениями

Клиент отправляет на сервер авторизованные запросы на отправку/получение сообщений и получение списка пользователей.
Пользователям с правами user доступно получение сообщения по id и получение списка сообщений в указанном диапазоне дат.
Пользователю с правами администратора также доступно получение списка всех пользователей и отправка (сохранение)
сообщений.
*Для просмотра всех эндпоинтов и удобства тестирования добавлен Swagger (доступен на localhost:8008/swagger-ui/index.html#)*
#### Рекомендации для сборки и запуска проекта:
```
-Перед стартом приложения должно быть запущено приложение сервер(micromessage).
```
- Перейти в корень проекта
- Создать исполняемый **jar** командой  **mvn clean package** (или **mvn clean package --D maven.test.skip **)
- Запустить **jar** файл из командной строки: **java -jar target/micromessagemate-1.0.jar**
- Либо **mvn spring-boot:run** (сборка и старт)
- Приложение стартует на порту 8008.


  **Для тестирования приложения локально добавлен Swagger:** (доступен на localhost:8008/swagger-ui/index.html#)

  **Или через Postman**
- отправка сообщения: GET запрос http://localhost:8008/api/message - в теле запроса ввести текст сообщения
- получение списка сообщений в пределах дат POST запрос 
  http://localhost:8008/api/message/from/2022-03-23T08:23:32/to/2022-03-25T21:23:32
даты в формате: yyyy-mm-ddThh:mm:ss