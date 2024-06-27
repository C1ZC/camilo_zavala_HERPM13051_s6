# camilo_zavala_HERPM13051_s6

## Descripción
Este repositorio contiene un proyecto en Kotlin estructurado para desarrollo en Android. Incluye los archivos y directorios necesarios para construir y gestionar el proyecto utilizando Gradle.

## Estructura del Proyecto
- `.idea/`: Contiene archivos del proyecto de IntelliJ IDEA.
- `app/`: Código fuente y recursos para la aplicación Android.
- `gradle/wrapper/`: Archivos de wrapper de Gradle para entornos de construcción consistentes.
- `.gitignore`: Especifica archivos y directorios que deben ser ignorados por Git.
- `build.gradle.kts`: Script de construcción en Kotlin DSL para el proyecto.
- `gradle.properties`: Propiedades de configuración para Gradle.
- `gradlew` / `gradlew.bat`: Scripts de wrapper de Gradle para Unix/Windows.
- `settings.gradle.kts`: Configuraciones para la construcción con Gradle.

## Comenzando
1. Clona el repositorio:
    ```sh
    git clone https://github.com/C1ZC/camilo_zavala_HERPM13051_s6.git
    ```
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle.
4. Construye y ejecuta la aplicación en un emulador o dispositivo físico.

## Contribuyendo
No dudes en enviar problemas o solicitudes de extracción. Para cambios mayores, por favor abre un problema primero para discutir lo que te gustaría cambiar.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT.

---

### Lenguajes Utilizados
- Kotlin

### Autor
Camilo Zavala

____________________________________________________________________________________________________
# Desarrollo de la Actividad

Imagina que has sido contratado como desarrollador de aplicaciones móviles para la Municipalidad de Pelotillehue, la cual requiere generar una aplicación móvil en Android para registrar las infracciones cursadas a locales comerciales. Los requerimientos para la aplicación son los siguientes:

## Requerimientos del Proyecto

- **Nombre del Proyecto:** El nombre del proyecto debe ser su nombre_apellido_seccioncurso.
- **Lenguaje de Programación:** La aplicación debe ser desarrollada en lenguaje Kotlin (aplicativo en lenguaje Java no será considerado).
- **Registro de Datos:** La aplicación debe registrar los siguientes datos de manera persistente en SQLlite:
   - RUT del inspector
   - Nombre del local comercial
   - Dirección
   - Infracción cometida
- **Asignación de Folio:** Cuando la infracción sea registrada, deberá mostrar en pantalla el folio asignado a dicha infracción.
- **Modificación de Datos:** Debe permitir la modificación de los datos de la infracción exceptuando el folio.
- **Listado de Infracciones:** La aplicación debe listar las infracciones ingresadas.
- **Compartir Datos:** La aplicación debe ser capaz de compartir los datos con otras aplicaciones.

---

Este proyecto tiene como objetivo facilitar el proceso de registro y gestión de infracciones en locales comerciales por parte de los inspectores de la Municipalidad de Pelotillehue, asegurando la persistencia y la accesibilidad de los datos registrados.
