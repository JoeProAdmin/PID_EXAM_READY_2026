# PID_EXAM_READY_2026

Projet Réservations réalisé avec Spring Boot.

## Dépôt GitHub

https://github.com/JoeProAdmin/PID_EXAM_READY_2026

## Technologies

- Java 17
- Spring Boot 2.4.5
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- Maven Wrapper
- Flyway
- MySQL / MariaDB XAMPP
- Git et GitHub

## Architecture

Le projet respecte l’architecture Spring Boot suivante :

- `model` : entités JPA, repositories et services ;
- `controller` : routes MVC et API ;
- `templates` : vues Thymeleaf ;
- `db/migration` : migrations Flyway ;
- `test` : tests unitaires et test de démarrage ;
- `LISEZ-MOI-*.md` : documentation technique des fonctionnalités.

## Base de données

Base locale :

```text
reservations_boot