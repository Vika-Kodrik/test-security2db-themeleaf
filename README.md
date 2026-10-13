# Лабораторная работа №8 — Spring Security

Веб-приложение **Student Management System** на Spring Boot 3 + Spring Security 6 + Thymeleaf + H2 + Spring Data JPA + Lombok + Bootstrap 5.

## Что сделано по сравнению с оригинальной ЛР

| Оригинал (ЛР 2022) | Актуализированная версия |
|---|---|
| Spring Boot 2.7.8 | Spring Boot 3.4.1 |
| Java 17 | Java 21 (LTS) |
| `javax.persistence.*` | `jakarta.persistence.*` (Spring Boot 3 / Jakarta EE 10) |
| `thymeleaf-extras-springsecurity5` | `thymeleaf-extras-springsecurity6` |
| `WebSecurityConfigurerAdapter` (deprecated) | `SecurityFilterChain` bean + lambda DSL |
| H2 console требовала `if (path.equals("/h2-console/**")) ... frameOptions().disable()` | `headers.frameOptions().sameOrigin()` — оставляет защиту для всего остального |
| `th:field="*{id}"` без валидации | `@Valid` + `BindingResult` + сообщения об ошибках |
| Пароль хранится plain-текстом (подразумевается в оригинале) | `BCryptPasswordEncoder` |
| Bootstrap 5.0.2 c integrity-hash | Bootstrap 5.3.3 без integrity (современная практика) |
| DataTables 1.10.25 под bs4 | DataTables 2.1.8 под bs5 |
| `ml-2` (Bootstrap 4) | `ms-2` (Bootstrap 5) |
| `ddl-auto=create` | `create-drop` — корректное завершение при остановке |
| — | Seed-данные: админ `admin@urfu.ru` / `admin` (создаётся автоматически) |
| — | Подтверждение пароля (`matchingPassword`) на форме регистрации |
| — | Кнопки `Update` / `Delete` для студентов, обновление на той же форме `add-student-form` |
| — | Страница `access-denied` для `hasRole('ADMIN')` |

## Запуск

```bash
mvn spring-boot:run
```

Откройте [http://localhost:8080](http://localhost:8080).

### Тестовые пользователи

| Email | Пароль | Роли |
|---|---|---|
| `admin@urfu.ru` | `admin` | `ROLE_USER`, `ROLE_ADMIN` |

Любой зарегистрированный через `/register` пользователь получает `ROLE_USER`.

### H2 Console

`http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:file:./testdb/students;AUTO_SERVER=TRUE`
User: `sa` (пустой пароль)

## Структура

```
src/main/java/ru/urfu/security
├── TestSecurity2dbThemeleafApplication.java   # entry point
├── entity/        # Role, Student, User (@Entity, Lombok)
├── repository/    # Spring Data JPA
├── dto/           # UserDto, StudentDto
├── service/       # StudentService, RoleService, UserService (+ Impl)
├── config/        # MvcConfig, WebSecurityConfig, UserDetailsServiceImpl, DataInitializer
└── controller/    # AppController, StudentController, MainController

src/main/resources
├── application.properties
└── templates/     # index, login, register, list-students, add-student-form, users, access-denied
```

## Использованные URL

| Метод | URL | Доступ |
|---|---|---|
| GET | `/` | redirect → `/index` |
| GET | `/index` | все |
| GET | `/login` | все |
| GET | `/register` | все |
| POST | `/register/save` | все |
| GET | `/list` | аутентифицированные |
| GET | `/addStudentForm` | аутентифицированные |
| POST | `/saveStudent` | аутентифицированные |
| GET | `/showUpdateForm?studentId=…` | аутентифицированные |
| GET | `/deleteStudent?studentId=…` | аутентифицированные |
| GET | `/users` | только `ROLE_ADMIN` |
| GET | `/logout` | аутентифицированные |
| GET | `/access-denied` | все |
| GET | `/h2-console/**` | все (для разработки) |
