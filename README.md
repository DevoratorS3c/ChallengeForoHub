# Spring Boot API con Seguridad JWT y CRUD para Tópicos

## Descripción del Proyecto
Este proyecto forma parte de los Challenge de ALURA LATAM
Este proyecto es una API RESTful desarrollada con Spring Boot que permite la gestión de tópicos. La API incluye operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y está protegida con autenticación JWT (JSON Web Token). Los usuarios deben autenticarse para interactuar con la API.
MADE BY David Almazan Moya

## Tecnologías Utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Lombok
- Flyway Migration
- MySQL
- JWT (JSON Web Token)
- Maven

## Dependencias

Las siguientes dependencias están incluidas en el proyecto:

- `Lombok`
- `Spring Web`
- `Spring Boot DevTools`
- `Spring Data JPA`
- `Flyway Migration`
- `MySQL Driver`
- `Validation`
- `Spring Security`

## Estructura del Proyecto

El proyecto tiene la siguiente estructura de paquetes:

- `config`: Contiene las configuraciones de seguridad.
- `controller`: Contiene los controladores REST.
- `dto`: Contiene las clases DTO (Data Transfer Objects).
- `model`: Contiene las entidades JPA.
- `repository`: Contiene las interfaces de repositorio JPA.
- `service`: Contiene las clases de servicio.

## Diagrama de la Base de Datos

![Diagrama de la Base de Datos](path/to/your/database-diagram.png)

## Funcionalidades

### Registro de Tópicos

- **Endpoint**: `POST /topicos`
- **Descripción**: Permite el registro de nuevos tópicos.
- **Request Body**: JSON con `titulo`, `mensaje`, `autor`, `curso`.
- **Validaciones**:
  - Todos los campos son obligatorios.
  - No se permite el registro de tópicos duplicados (mismo título y mensaje).

### Listado de Tópicos

- **Endpoint**: `GET /topicos`
- **Descripción**: Devuelve la lista de todos los tópicos.
- **Response Body**: JSON con los detalles de los tópicos.

### Detalle de Tópico

- **Endpoint**: `GET /topicos/{id}`
- **Descripción**: Devuelve los detalles de un tópico específico.
- **Path Variable**: `id` del tópico.

### Actualización de Tópico

- **Endpoint**: `PUT /topicos/{id}`
- **Descripción**: Actualiza los datos de un tópico existente.
- **Path Variable**: `id` del tópico.
- **Request Body**: JSON con los nuevos datos del tópico.

### Eliminación de Tópico

- **Endpoint**: `DELETE /topicos/{id}`
- **Descripción**: Elimina un tópico específico.
- **Path Variable**: `id` del tópico.

## Seguridad

### Autenticación JWT

La API utiliza JWT para la autenticación. Los usuarios deben autenticarse para obtener un token JWT, que luego se usa para acceder a los endpoints protegidos.

### Endpoints Públicos

- **Endpoint**: `POST /login`
- **Descripción**: Permite a los usuarios autenticarse y obtener un token JWT.

### Configuración de Seguridad

La configuración de seguridad se encuentra en la clase `SecurityConfigurations`.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    private final UserService userService;
    private final TokenService tokenService;

    public SecurityConfigurations(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), tokenService), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtAuthorizationFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}