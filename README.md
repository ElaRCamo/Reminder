````markdown
# Reminder — App de recordatorios (CRUD)

Aplicación Java para crear, leer, actualizar y eliminar recordatorios y guardar “tips” o cosas por hacer (CRUD). Base simple para practicar arquitectura por capas, persistencia y empaquetado.

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
```

## Requisitos

* Java 8+ (recomendado 11 o 17).
* IntelliJ IDEA o JDK con `javac/java`.
* Opcional: SQL Server local o en contenedor.


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

## Licencia

MIT. Consulta el archivo `LICENSE`.

## Agradecimientos

* Comunidad Java.
* Quienes reportan issues y envían PRs.
* Proyecto jTDS para conexión con SQL Server.

## Autoría

ElaRCamo — Application that gives you reminders and you can save tips and things to do (CRUD).

