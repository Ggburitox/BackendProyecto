# Spring Boot

[Presentación de Google Slides](https://docs.google.com/presentation/d/1BFrt7UhHbkl7WQwjj7DJkyL4Kx7mPQezNvuN3AZQw7c/edit?usp=sharing)

Setup:
- Enable Environment Variables from Run/Debug configuration
- Add the following environment variables:
    - `JWT_SECRET=secret;`
    - `PG_HOST=localhost;`
    - `PG_PORT=5432;`
    - `PGPASSWORD=postgres;` (depends on your pgadmin configuration)
    - `PGUSER=postgres;` (depends on your pgadmin configuration)
    - `POSTGRES_DB=postgres;`
    - `MAIL_HOST=smtp.gmail.com;`
    - `MAIL_PORT=587;`
    - `MAIL_USERNAME` (set your own email)
    - `MAIL_PASSWORD` (set your [password](https://accounts.google.com/v3/signin/challenge/pwd?TL=ALoj5ArB20wHD9sc3F4rEJlcw9FvQugyRC2rNOHdo2iZinu9Xb11HuQiqbFauvRj&cid=2&continue=https%3A%2F%2Fmyaccount.google.com%2Fapppasswords&flowName=GlifWebSignIn&followup=https%3A%2F%2Fmyaccount.google.com%2Fapppasswords&ifkv=AdF4I745rF1RqoN2BY0SSIAPszMVr17BfRW8Vy_5G3AlYVffJcsT5--PdIQerqjfpwasNs0XWZzL5w&osid=1&rart=ANgoxccfsubUEl7wDaFLYZx-iLGEYqYihWwg5_amPSMGP3XX16OMZO_diRznsj_UxDkb_FcZSHfcnuYihLA8Up5eI5ZxeHJsWsqrpFANADe5DPpKcNx2-mA&rpbg=1&service=accountsettings) for the application)
