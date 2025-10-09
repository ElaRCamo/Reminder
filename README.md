
````markdown
# Reminder — App de recordatorios (CRUD)

[![Estado](https://img.shields.io/badge/estado-activo-8a2be2)]()
[![Hecho%20con](https://img.shields.io/badge/hecho_con-Java-007396)]()
[![Lenguaje%20principal](https://img.shields.io/github/languages/top/ElaRCamo/Reminder)]()
[![Commits](https://img.shields.io/github/commit-activity/m/ElaRCamo/Reminder)]()
[![Issues](https://img.shields.io/github/issues/ElaRCamo/Reminder)]()
[![Licencia](https://img.shields.io/badge/licencia-MIT-00aa00)]()

Aplicación **Java** para **crear, leer, actualizar y eliminar** recordatorios y **guardar “tips” o cosas por hacer** de manera sencilla (CRUD). Pensada como base para practicar patrones de arquitectura, persistencia y empaquetado.

> Actualmente el repo es 100% Java y contiene un proyecto IntelliJ (`.iml`) con código en `src/`. Se incluye un `jtds-1.3.1.jar` (driver para SQL Server), ideal si deseas activar persistencia. :contentReference[oaicite:1]{index=1}

---

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

---

## Características

- 📌 **Recordatorios/Tareas** con operaciones CRUD.
- 🧠 **Tips/Notas rápidas** para no olvidar pequeñas acciones.
- 💾 **Persistencia opcional** con **SQL Server** vía **jTDS** (`jtds-1.3.1.jar`) si decides activarla.
- 🧱 Base limpia para aplicar capas **UI → Servicio → DAO → DB**.
- 🧪 Preparada para crecer con pruebas, empaquetado y CI.

---

## Demo / Capturas

> Añade tus imágenes en `docs/images/` y enlázalas aquí.

| Lista principal | Crear/Editar | Detalle |
|---|---|---|
| ![Lista](docs/images/lista.png) | ![Formulario](docs/images/form.png) | ![Detalle](docs/images/detalle.png) |

También puedes incluir un GIF corto (`docs/images/demo.gif`) mostrando el flujo.

---

## Arquitectura

Diagrama lógico propuesto (puede variar según tu implementación):

```mermaid
flowchart TB
  UI[UI (Swing/JavaFX/AWT)] --> SVC[Servicio (lógica)]
  SVC --> DAO[(DAO/Repositorio)]
  DAO --> DB[(SQL Server - opcional)]
  SVC --> MEM[(In-Memory)]
````

* **UI**: pantallas y componentes.
* **Servicio**: validaciones, orquestación y reglas.
* **DAO**: acceso a datos (in-memory o BD).
* **BD**: SQL Server opcional vía **jTDS**.

---

## Requisitos

* **Java 8+** (recomendado 11 o 17).
* **IntelliJ IDEA** (Community o Ultimate) *o* JDK con `javac/java`.
* **Opcional** (si activas persistencia):

  * SQL Server en local o contenedor.
  * Archivo de configuración con credenciales de BD.

---

## Instalación

### Opción A: IntelliJ IDEA

1. **Clona** el repositorio:

   ```bash
   git clone https://github.com/ElaRCamo/Reminder.git
   cd Reminder
   ```
2. **Abre** el proyecto en IntelliJ (`File > Open…` y selecciona la carpeta).
3. Verifica que el **SDK** de Java esté configurado en `Project Structure`.
4. **Ejecuta** la clase principal (por ejemplo, `Main` en `src/…`) desde el ícono ▶️.

> El repo incluye `Reminder.iml` e `.idea/` (metadatos de IntelliJ). ([GitHub][1])

### Opción B: Compilar por consola

Assumiendo que el código fuente está bajo `src/`:

```bash
# 1) Compilar
javac -d out $(find src -name "*.java")

# 2) Ejecutar (reemplaza paquete/clase)
java -cp out Main
```

Si usas el driver jTDS en modo BD:

```bash
# Incluye el jar en el classpath
java -cp "out:jtds-1.3.1.jar" Main
```

---

## Configuración (BD opcional)

Si deseas usar **SQL Server**:

1. Asegúrate de tener **SQL Server** corriendo (local o Docker).
2. Crea un archivo `config.properties` (o variables de entorno) con:

   ```properties
   db.driver=net.sourceforge.jtds.jdbc.Driver
   db.url=jdbc:jtds:sqlserver://localhost:1433/ReminderDB
   db.user=sa
   db.password=TuPasswordSegura
   ```
3. En tu inicialización de DAO, **carga estas propiedades** y crea el `Connection`.
4. Asegura que `jtds-1.3.1.jar` esté en el **classpath** al ejecutar.

> El jar `jtds-1.3.1.jar` está en la raíz del repo. ([GitHub][1])

**Tip (Docker para SQL Server dev):**

```bash
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=TuPassword123!" \
  -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2022-latest
```

---

## Uso

1. Inicia la app.
2. **Crea** un recordatorio o **añade** un “tip”.
3. **Edita/Marca** como completado lo que corresponda.
4. **Elimina** lo que ya no necesites.

> Si estás en modo **in-memory**, todos los datos se pierden al cerrar (útil para pruebas).
> En modo **BD**, los cambios persisten en SQL Server.

---

## Estructura del repositorio

```
Reminder/
├─ src/                    # Código fuente Java
├─ jtds-1.3.1.jar         # Driver SQL Server (jTDS)
├─ Reminder.iml           # Módulo IntelliJ
├─ .idea/                 # Configuración IntelliJ
├─ .gitignore
```

> Estructura e items observados en GitHub. ([GitHub][1])

---

## Roadmap

* [ ] Empaquetado ejecutable (JAR con `Main-Class`).
* [ ] Añadir **tests** (JUnit + Mockito) para servicios/DAO.
* [ ] Abstracción de persistencia (interfaz `Repository`, implementaciones in-memory/SQL).
* [ ] UI moderna (JavaFX) o migración a cliente web.
* [ ] CI en GitHub Actions (build + tests).
* [ ] Exportación/Importación (CSV/JSON).
* [ ] Internacionalización (i18n).

---

## Guía para contribuir

1. Haz un **fork** y crea rama a partir de `master`: `feat/mi-aporte`.
2. Sigue convención de commits (p. ej., *Conventional Commits*).
3. Si tocas persistencia, añade **pruebas** y documentación.
4. Abre un **Pull Request** con descripción clara y capturas si aplica.

---

## Código de conducta

Este proyecto promueve un entorno de respeto y colaboración. Recomendado incluir `CODE_OF_CONDUCT.md` (p. ej., Contributor Covenant).

---

## Licencia

Este proyecto se publica bajo **MIT** (licencia permisiva).
Consulta `LICENSE` para el texto completo.

---

## Agradecimientos

* A la comunidad Java por librerías y guías.
* A quienes reportan issues y envían PRs.
* **jTDS** por el driver JDBC para SQL Server (si habilitas persistencia).

---

### Autoría

**ElaRCamo** — *Application that gives you reminders and you can save tips and things to do (CRUD)*. ([GitHub][1])
