package com.example.inmueblecheck.data.model;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          Message.java
 * Layer:         Data / Model (Domain Entity)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Representa un mensaje individual intercambiado dentro de la subcolección
 * "messages" de un chat en Firestore, con marca de tiempo del servidor.
 *
 * ARCHIVOS RELACIONADOS:
 * - Chat.java                -> Hilo contenedor del mensaje.
 * - ChatAdapter.java         -> Infla burbujas de texto.
 * - ChatViewModel.java       -> Envía y escucha mensajes.
 * - ConversacionFragment.java-> Vista donde se redactan.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - POJO serializado para mensajes de Firestore.
 * ============================================================================
 */
public class Message {
    private String text;
    private String senderId;
    private String senderEmail;
    @ServerTimestamp
    private Date timestamp;

    public Message() {}

    public Message(String text, String senderId, String senderEmail) {
        this.text = text;
        this.senderId = senderId;
        this.senderEmail = senderEmail;
    }

    // Getters y Setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
