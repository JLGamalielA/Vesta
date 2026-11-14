package com.example.inmueblecheck.ui.config;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.inmueblecheck.R;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          AboutFragment.java
 * Layer:         UI / View (Configuración - Acerca De)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Pantalla informativa que expone los datos institucionales de la aplicación
 * Vesta, versión del software, créditos y términos de uso.
 *
 * ARCHIVOS RELACIONADOS:
 * - ConfiguracionFragment.java -> Pantalla previa de navegación.
 * - fragment_about.xml         -> Maquetación visual estática informativa.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Hilo principal (UI Thread).
 * ============================================================================
 */
public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}