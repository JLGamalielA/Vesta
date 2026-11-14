VESTA:
VESTA es una aplicación Android nativa diseñada para facilitar la gestión y búsqueda de propiedades inmobiliarias. Conecta a arrendadores (gerentes) con arrendatarios (agentes).

Tecnologías Utilizadas:
Lenguaje: Java 11 / 17
Arquitectura: MVVM (Model-View-ViewModel)
Base de Datos: Firebase Firestore & Realtime Database
Autenticación: Firebase Auth
Almacenamiento Local: Room Database (para funcionamiento offline)
Mapas: Google Maps SDK
UI: Material Design 3, ConstraintLayout, RecyclerView

Configuración del Proyecto:
Para ejecutar este proyecto localmente, necesitas configurar Firebase:
1. Clona el repositorio.
2. Crea un proyecto en Firebase Console.
3. Habilita Authentication (Email/Password) y Firestore Database.
4. Descarga el archivo google-services.json de tu proyecto Firebase.
5. Pega el archivo en la carpeta app/ del proyecto (este archivo está ignorado por git por seguridad).
6. Sincroniza el proyecto con Gradle y ejecuta.
