package com.example.inmueblecheck.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmueblecheck.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          RegisterFragment.java
 * Layer:         UI / View (Autenticación - Registro)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Pantalla de registro de nuevos usuarios en la plataforma. Captura datos
 * personales, email y contraseña, delegando la creación de cuenta y asignación
 * inicial de rol en Firebase.
 *
 * ARCHIVOS RELACIONADOS:
 * - AuthViewModel.java      -> Gestiona creación de cuenta.
 * - LoginFragment.java      -> Retorno tras registro exitoso.
 * - fragment_register.xml   -> Interfaz formulario de registro.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Hilo principal (UI Thread); observador reactivo LiveData.
 * ============================================================================
 */
public class RegisterFragment extends Fragment {

    private AuthViewModel authViewModel;
    private TextInputEditText etNombre, etEmail, etPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    public RegisterFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        etNombre = view.findViewById(R.id.etNombre);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        tvGoToLogin = view.findViewById(R.id.tvGoToLogin);

        authViewModel.getAuthResult().observe(getViewLifecycleOwner(), authResultState -> {
            if (authResultState == null) return;

            switch (authResultState.status) {
                case LOADING:
                    btnRegister.setEnabled(false);
                    btnRegister.setText("Registrando...");
                    break;
                case REGISTRATION_SUCCESS:
                    btnRegister.setEnabled(true);
                    btnRegister.setText("Registrarse");
                    Toast.makeText(getContext(), "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).popBackStack();
                    break;
                case ERROR:
                    btnRegister.setEnabled(true);
                    btnRegister.setText("Registrarse");
                    Toast.makeText(getContext(), authResultState.errorMessage, Toast.LENGTH_LONG).show();
                    break;
            }
        });

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // AHORA ENVIAMOS EL NOMBRE TAMBIÉN
            authViewModel.register(nombre, email, password, "arrendatario");
        });

        tvGoToLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });
    }
}