package com.example.inmueblecheck.data.model;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          User.java
 * Layer:         Data / Model (Domain Entity)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Entidad de dominio que modela el perfil de un usuario autenticado en el
 * sistema Vesta (arrendador o arrendatario), almacenado en Cloud Firestore.
 *
 * ARCHIVOS RELACIONADOS:
 * - AuthViewModel.java        -> Gestiona registro e inicio.
 * - ConfiguracionFragment.java-> Despliega datos de perfil.
 * - LoginFragment.java        -> Punto de acceso inicial.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - POJO de sesión y mapeo Firestore.
 * ============================================================================
 */
public class User {
    private String uid;
    private String nombre;
    private String email;
    private String role;

    public User() {}

    public User(String uid, String nombre, String email, String role) {
        this.uid = uid;
        this.nombre = nombre;
        this.email = email;
        this.role = role;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return (nombre != null && !nombre.isEmpty()) ? nombre : (email != null ? email : "Usuario");
    }
}
