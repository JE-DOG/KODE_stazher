# KODE_stazher

## Stack

### Architecture

- Clean architecture
- MVI
- Multi modules

### UI

- Jetpack Compose Navigation
- Compose

### Network

- Retrofit
- Gson

### **Asynchrony**

- Coroutines
- Flow

### DI

- Koin

### Other

- Single Activity
- Kotlin
- Coin

## Criteries

- на полноту реализованного функционала и соответствие дизайну
- количество ошибок
- стиль и оформление кода
- работу с git - оформление commits, pull requests
- декомпозицию и оценку - то есть на твоё умение разбивать задачи и планировать собственное время


## API

Запрос для получения успешного ответа:

```
curl --request GET \
  --url https://stoplight.io/mocks/kode-education/trainee-test/25143926/users \
  --header 'Content-Type: application/json' \
  --header 'Prefer: code=200, example=success'
```

Запрос для тестирования на разных данных (генерируется автоматически при каждом запросе):

```
curl --request GET \
  --url https://stoplight.io/mocks/kode-education/trainee-test/25143926/users \
  --header 'Content-Type: application/json' \
  --header 'Prefer: code=200, dynamic=true'
```

Запрос, который вернет ошибку с http кодом 500:

```
curl --request GET \
  --url https://stoplight.io/mocks/kode-education/trainee-test/25143926/users \
  --header 'Content-Type: application/json' \
  --header 'Prefer: code=500, example=error-500'
```


# Sources

API - https://kode-education.stoplight.io/docs/trainee-test/b3A6MjUxNDM5Mjg-get-users

Design (Figam) - https://www.figma.com/file/GRRKONipVClULsfdCAuVs1/KODE-Trainee-Dev-%D0%9E%D1%81%D0%B5%D0%BD%D1%8C'21?node-id=11%3A14413

Task documentation - https://github.com/appKODE/trainee-test-android
