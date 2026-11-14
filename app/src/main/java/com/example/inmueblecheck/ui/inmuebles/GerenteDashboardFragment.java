package com.example.inmueblecheck.ui.inmuebles;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmueblecheck.ui.adapters.InmuebleAdapter;
import com.example.inmueblecheck.R;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          GerenteDashboardFragment.java
 * Layer:         UI / View (Inmuebles - Gerente)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Panel de control para el perfil de gerente. Permite supervisar el inventario
 * inmobiliario global, visualizar estados de sincronización y navegar a la
 * creación de nuevas propiedades.
 *
 * ARCHIVOS RELACIONADOS:
 * - GerenteViewModel.java         -> Orquesta estado y datos.
 * - InmuebleAdapter.java          -> Despliega tarjetas de inmuebles.
 * - CrearInmuebleFragment.java    -> Pantalla de registro propiedades.
 * - fragment_gerente_dashboard.xml-> Diseño visual del panel.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Hilo principal (UI Thread); observador reactivo de LiveData.
 * ============================================================================
 */
public class GerenteDashboardFragment extends Fragment {

    private GerenteViewModel viewModel;
    private RecyclerView recyclerView;
    private InmuebleAdapter adapter;
    private ProgressBar progressBar;
    private MaterialToolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gerente_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewInspecciones);
        progressBar = view.findViewById(R.id.progressBar);
        toolbar = view.findViewById(R.id.toolbarGerente);

        viewModel = new ViewModelProvider(requireActivity()).get(GerenteViewModel.class);
        progressBar.setVisibility(View.VISIBLE);

        setupRecyclerView();
        setupObservers();
    }

    private void setupRecyclerView() {
        adapter = new InmuebleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Ver Detalle
        adapter.setOnInmuebleClickListener(inmueble -> {
            if (inmueble != null && inmueble.getUid() != null) {
                Bundle args = new Bundle();
                args.putString("inmuebleId", inmueble.getUid());
                try {
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_gerente_to_detalle, args);
                } catch (Exception e) { Log.e("Nav", "Error", e); }
            }
        });

        // Editar
        adapter.setOnEditarClickListener(inmueble -> {
            if (inmueble != null && inmueble.getUid() != null) {
                Bundle args = new Bundle();
                args.putString("editInmuebleId", inmueble.getUid());
                try {
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_gerenteDashboardFragment_to_crearInmuebleFragment, args);
                } catch (Exception e) { Log.e("Nav", "Error", e); }
            }
        });

        // Mover a Historial
        adapter.setOnEstadoClickListener(inmueble -> {
            String accion = "Venta".equalsIgnoreCase(inmueble.getTipoTransaccion()) ? "vendido" : "rentado";
            new AlertDialog.Builder(getContext())
                    .setTitle("¿Marcar como " + accion + "?")
                    .setMessage("El inmueble se moverá al historial y dejará de ser visible para los clientes.")
                    .setPositiveButton("Sí", (d, w) -> {
                        viewModel.alternarEstadoInmueble(inmueble);
                        Toast.makeText(getContext(), "Inmueble movido al historial", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void setupObservers() {
        // Observamos SOLO los ACTIVOS
        viewModel.getMisInmueblesActivos().observe(getViewLifecycleOwner(), inmuebles -> {
            adapter.setInmuebles(inmuebles);
            progressBar.setVisibility(View.GONE);
        });
    }
}