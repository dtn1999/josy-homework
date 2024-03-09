# Dokumentation

## Inhalt

- [Systemanforderungen](#systemanforderungen)
- [Projektstruktur](#project-structure)
- [Starten Die Anwendung](#starten-die-anwendung)
  - [Backend](#backend)
  - [Frontend](#frontend)
- [Installation](#installation)
  - [Backend](#backend)
  - [Frontend](#frontend)
- [Use Cases](#use-cases)

### Systemanforderungen
Um das Projekt zu starten, wird folgendes benötigt:
- [Java 21](https://openjdk.org/projects/jdk/21/): Die backend Anwendung ist in Java geschrieben und benötigt Java 21. 
  Im Fall, dass Java 21 nicht installiert ist, die folgende Anleitung kann benutzt werder: [Java 21 Installation](https://www.oracle.com/fr/java/technologies/downloads/#jdk21-linux)

- [Node 20](). Die folgende Anleitung kann benutzt werden um Node 20 zu installieren:
  - [windows](https://prototype-kit.service.gov.uk/docs/install/node-windows)
  - [linux](https://joshtronic.com/2023/04/23/how-to-install-nodejs-20-on-ubuntu-2004-lts/)


### Projektstruktur
Das Projekt besteht aus zwei Teilen: Backend und Frontend.
- Backend: Die backend Anwendung ist in Java geschrieben und benutzt Spring Boot. Die Anwendung ist in `backend` Ordner.
  und benutzt H2 Database als Datenbank.
- Frontend: Die frontend Anwendung ist a TypeScript Anwendung und benutzt Next.js. Die Anwendung ist in `frontend` Ordner.

### Starten Die Anwendung
Um die Anwendung zu starten, müssen Sie zuerst die Backend Anwendung starten und dann die Frontend Anwendung.

#### Backend
Um die Backend Anwendung zu starten, navigieren in `backend` Ordner und führen Sie folgende Befehle aus:

```bash
# Window
gradlew.bat bootRun
# Linux
./gradlew bootRun
```

#### Frontend
Um die Frontend Anwendung zu starten, navigieren in `frontend` Ordner und führen Sie folgende Befehle aus:

```bash
npm install
npm run dev
```

### Application Use cases
In this section of the documentation, we will describe the use cases of the application. The application is a simple
`note taking` application. The application has the following use cases:

1. User management
Every user can register and login to the application. Moreover, the user can log out or even delete his account. The
screenshots below show the user management use cases.

- Register
  ![](./images/register-screen.png)

- Login
  ![](./images/login-screen.png) 

- Forgot password
  ![](./images/forgot-password-screen.png)

2. Note management
Every user can create, update, delete and view his notes. The screenshots below show the note management use cases.

- View notes
  ![](./images/view-notes-screen.png)

- View a single note
  ![](./images/view-note-screen.png)

- Create note
  ![](./images/create-note-screen.png)