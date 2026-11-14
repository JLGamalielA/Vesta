package com.example.inmueblecheck.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmueblecheck.R;
import com.example.inmueblecheck.data.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          ChatAdapter.java
 * Layer:         UI / Adapters (Mensajería)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Adaptador RecyclerView para mensajes individuales de chat. Discrimina
 * visualmente entre mensajes entrantes y salientes según el UID del usuario
 * actual y formatea marcas de tiempo.
 *
 * ARCHIVOS RELACIONADOS:
 * - Message.java              -> Modelo mensaje individual renderizado.
 * - item_message_sent.xml     -> Burbuja de mensaje enviado.
 * - item_message_received.xml -> Burbuja de mensaje recibido.
 * - ConversacionFragment.java -> Aloja la conversación interactiva.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Hilo principal (UI Thread).
 * ============================================================================
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<Message> messages = new ArrayList<>();
    private final String currentUserId;

    public ChatAdapter() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            currentUserId = "";
        }
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message, currentUserId);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessageMe, tvTimeMe;
        TextView tvMessageOther, tvTimeOther;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessageMe = itemView.findViewById(R.id.tvMessageMe);
            tvTimeMe = itemView.findViewById(R.id.tvTimeMe);
            tvMessageOther = itemView.findViewById(R.id.tvMessageOther);
            tvTimeOther = itemView.findViewById(R.id.tvTimeOther);
        }

        public void bind(Message message, String currentUserId) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String time = (message.getTimestamp() != null)
                    ? sdf.format(message.getTimestamp())
                    : "Enviando...";

            if (message.getSenderId() != null && message.getSenderId().equals(currentUserId)) {
                // Es MI mensaje -
                tvMessageMe.setVisibility(View.VISIBLE);
                tvTimeMe.setVisibility(View.VISIBLE);
                tvMessageOther.setVisibility(View.GONE);
                tvTimeOther.setVisibility(View.GONE);

                tvMessageMe.setText(message.getText());
                tvTimeMe.setText(time);
            } else {
                // Es mensaje de OTRO
                tvMessageMe.setVisibility(View.GONE);
                tvTimeMe.setVisibility(View.GONE);
                tvMessageOther.setVisibility(View.VISIBLE);
                tvTimeOther.setVisibility(View.VISIBLE);

                tvMessageOther.setText(message.getText());
                tvTimeOther.setText(time);
            }
        }
    }
}