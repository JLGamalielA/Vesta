package com.example.inmueblecheck.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          Media.java
 * Layer:         Data / Model (Domain Entity)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Entidad que representa un archivo multimedia (fotografía) adjunto a una
 * propiedad. Registra rutas locales en el dispositivo y URLs remotas en
 * Firebase Storage, junto con su estado de sincronización.
 *
 * ARCHIVOS RELACIONADOS:
 * - Inmueble.java           -> Propiedad a la que pertenece.
 * - InmuebleDao.java        -> Consultas de fotos pendientes.
 * - SyncWorker.java         -> Sube archivos a Storage.
 * - MediaAdapter.java       -> Muestra galería en UI.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - POJO persistido en SQLite mediante Room.
 * ============================================================================
 */
@Entity(tableName = "media_table")
public class Media {

    @PrimaryKey
    @NonNull
    private String mediaId;
    private String inspectionId;
    private String itemName;
    private String localUri;
    private String remoteUri;

    private String type;
    private boolean isSynced;

    public Media() {
        this.isSynced = false;
    }

    @NonNull
    public String getMediaId() { return mediaId; }
    public void setMediaId(@NonNull String mediaId) { this.mediaId = mediaId; }
    public String getInspectionId() { return inspectionId; }
    public void setInspectionId(String inspectionId) { this.inspectionId = inspectionId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getLocalUri() { return localUri; }
    public void setLocalUri(String localUri) { this.localUri = localUri; }
    public String getRemoteUri() { return remoteUri; }
    public void setRemoteUri(String remoteUri) { this.remoteUri = remoteUri; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isSynced() { return isSynced; }
    public void setSynced(boolean synced) { this.isSynced = synced; }
}
