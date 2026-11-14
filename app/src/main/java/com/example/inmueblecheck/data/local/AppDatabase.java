package com.example.inmueblecheck.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.TypeConverter;

import com.example.inmueblecheck.data.model.Inmueble;
import com.example.inmueblecheck.data.model.Media;

import java.util.Date;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          AppDatabase.java
 * Layer:         Data / Local (Room SQLite Database)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Base de datos local SQLite implementada mediante Android Jetpack Room.
 * Gestiona el patrón Singleton con sincronización de hilos ("double-checked locking")
 * y define los convertidores de tipo para fechas.
 *
 * ARCHIVOS RELACIONADOS:
 * - Inmueble.java           -> Tabla principal persistida.
 * - Media.java              -> Tabla de fotos persistida.
 * - InmuebleDao.java        -> Acceso a consultas SQL.
 * - InmuebleRepository.java -> Consume la instancia Singleton.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Inicialización segura entre hilos; consultas restringidas fuera del hilo UI.
 * ============================================================================
 */
class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) { return value == null ? null : new Date(value); }
    @TypeConverter
    public static Long dateToTimestamp(Date date) { return date == null ? null : date.getTime(); }
}

@Database(entities = {Inmueble.class, Media.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract InmuebleDao inmuebleDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "vesta_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
