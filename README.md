````markdown
# Reminder — App de recordatorios (CRUD)

[![Estado](https://img.shields.io/badge/estado-activo-8a2be2)]()
[![Hecho%20con](https://img.shields.io/badge/hecho_con-Java-007396)]()
[![Lenguaje%20principal](https://img.shields.io/github/languages/top/ElaRCamo/Reminder)]()
[![Commits](https://img.shields.io/github/commit-activity/m/ElaRCamo/Reminder)]()
[![Issues](https://img.shields.io/github/issues/ElaRCamo/Reminder)]()
[![Licencia](https://img.shields.io/badge/licencia-MIT-00aa00)]()

Aplicación Java para crear, leer, actualizar y eliminar recordatorios y guardar “tips” o cosas por hacer (CRUD). Base simple para practicar arquitectura por capas, persistencia y empaquetado.

## Tabla de contenidos

- [Características](#características)
- [Demo / Capturas](#demo--capturas)
- [Arquitectura](#arquitectura)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
  - [Opción A: IntelliJ IDEA](#opción-a-intellij-idea)
  - [Opción B: Compilar por consola](#opción-b-compilar-por-consola)
- [Configuración (BD opcional)](#configuración-bd-opcional)
- [Uso](#uso)
- [Estructura del repositorio](#estructura-del-repositorio)
- [Roadmap](#roadmap)
- [Guía para contribuir](#guía-para-contribuir)
- [Código de conducta](#código-de-conducta)
- [Licencia](#licencia)
- [Agradecimientos](#agradecimientos)
- [Autoría](#autoría)

## Características

- Recordatorios/Tareas con operaciones CRUD.
- Tips/Notas rápidas.
- Persistencia opcional con SQL Server vía jTDS.
- Estructura preparada para capas UI → Servicio → DAO → DB.
- Lista para crecer con pruebas y CI.

## Demo / Capturas

| Lista principal | Crear/Editar | Detalle |
|---|---|---|
| ![Lista](docs/images/lista.png) | ![Formulario](docs/images/form.png) | ![Detalle](docs/images/detalle.png) |

![Logo](docs/images/logo.png)

## Arquitectura

```mermaid
flowchart TB
  UI[UI (Swing/JavaFX/AWT)] --> SVC[Servicio (lógica)]
  SVC --> DAO[(DAO/Repositorio)]
  DAO --> DB[(SQL Server - opcional)]
  SVC --> MEM[(In-Memory)]
````

## Requisitos

* Java 8+ (recomendado 11 o 17).
* IntelliJ IDEA o JDK con `javac/java`.
* Opcional: SQL Server local o en contenedor.

## Instalación

### Opción A: IntelliJ IDEA

```bash
git clone https://github.com/ElaRCamo/Reminder.git
cd Reminder
```

Abrir el proyecto en IntelliJ y ejecutar la clase principal.

### Opción B: Compilar por consola

```bash
javac -d out $(find src -name "*.java")
java -cp out Main
```

Con driver jTDS en el classpath:

```bash
java -cp "out:jtds-1.3.1.jar" Main
```

## Configuración (BD opcional)

Archivo `config.properties`:

```properties
db.driver=net.sourceforge.jtds.jdbc.Driver
db.url=jdbc:jtds:sqlserver://localhost:1433/ReminderDB
db.user=sa
db.password=TuPasswordSegura
```

Contenedor de SQL Server para desarrollo:

```bash
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=TuPassword123!" -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2022-latest
```

## Uso

1. Iniciar la aplicación.
2. Crear un recordatorio o añadir un tip.
3. Editar, marcar como completado o eliminar.

## Estructura del repositorio

```
Reminder/
├─ src/
├─ jtds-1.3.1.jar
├─ docs/
│  └─ images/
├─ .idea/
├─ Reminder.iml
├─ .gitignore
└─ README.md
```

## Roadmap

* [ ] Empaquetado ejecutable (JAR con `Main-Class`).
* [ ] Pruebas (JUnit + Mockito).
* [ ] Interfaz `Repository` con implementaciones in-memory y SQL.
* [ ] UI moderna con JavaFX o cliente web.
* [ ] CI en GitHub Actions (build + tests).
* [ ] Exportación/Importación (CSV/JSON).
* [ ] Internacionalización (i18n).

## Guía para contribuir

1. Haz un fork y crea una rama desde `master` (`feat/mi-aporte`).
2. Usa commits descriptivos.
3. Añade pruebas si modificas lógica de negocio o DAO.
4. Abre un Pull Request con descripción y capturas si aplica.

## Código de conducta

Se promueve un entorno de respeto y colaboración. Puedes incluir `CODE_OF_CONDUCT.md` basado en Contributor Covenant.

## Licencia

MIT. Consulta el archivo `LICENSE`.

## Agradecimientos

* Comunidad Java.
* Quienes reportan issues y envían PRs.
* Proyecto jTDS para conexión con SQL Server.

## Autoría

ElaRCamo

```
::contentReference[oaicite:0]{index=0}
```
