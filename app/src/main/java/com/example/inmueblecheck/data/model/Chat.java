package com.example.inmueblecheck.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;
import java.util.List;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          Chat.java
 * Layer:         Data / Model (Domain Entity)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Entidad que modela un hilo de conversación en tiempo real entre un
 * interesado y el arrendador asociado a un inmueble específico en Cloud Firestore.
 *
 * ARCHIVOS RELACIONADOS:
 * - Message.java             -> Mensajes individuales contenidos.
 * - ChatViewModel.java       -> Maneja escuchas Firestore.
 * - ChatListAdapter.java    -> Lista hilos en pantalla.
 * - Inmueble.java            -> Propiedad motivo del chat.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Mapeo de documentos de la colección "chats" de Firestore.
 * ============================================================================
 */
public class Chat {
    @DocumentId
    private String chatId;
    private String inmuebleNombre;
    private String inmuebleId;
    private String lastMessage;
    @ServerTimestamp
    private Date lastTimestamp;
    private List<String> users;

    public Chat() {}

    // Getters y Setters
    public String getChatId() { return chatId; }
    public void setChatId(String chatId) { this.chatId = chatId; }
    public String getInmuebleNombre() { return inmuebleNombre; }
    public void setInmuebleNombre(String inmuebleNombre) { this.inmuebleNombre = inmuebleNombre; }
    public String getInmuebleId() { return inmuebleId; }
    public void setInmuebleId(String inmuebleId) { this.inmuebleId = inmuebleId; }
    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }
    public Date getLastTimestamp() { return lastTimestamp; }
    public void setLastTimestamp(Date lastTimestamp) { this.lastTimestamp = lastTimestamp; }
    public List<String> getUsers() { return users; }
    public void setUsers(List<String> users) { this.users = users; }
}
