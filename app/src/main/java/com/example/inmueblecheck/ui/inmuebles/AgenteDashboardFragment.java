package com.example.inmueblecheck.ui.inmuebles;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.inmueblecheck.R;
import com.example.inmueblecheck.data.model.Inmueble;
import com.example.inmueblecheck.ui.adapters.InmuebleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          AgenteDashboardFragment.java
 * Layer:         UI / View (Inmuebles - Agente)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * Vista principal del agente inmobiliario. Presenta el catálogo de inmuebles y
 * las propiedades del usuario mediante pestañas, buscador en tiempo real, filtros
 * dinámicos por tipo/precio, Pull-to-Refresh y navegación al detalle.
 *
 * ARCHIVOS RELACIONADOS:
 * - AgenteViewModel.java         -> Provee datos y filtros.
 * - InmuebleAdapter.java         -> Renderiza lista de inmuebles.
 * - DetalleInmuebleFragment.java -> Destino navegación al detalle.
 * - fragment_agente_dashboard.xml-> Layout XML de pantalla.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - Hilo principal (UI Thread); gestiona ciclo de vida de vistas AndroidX Fragment.
 * ============================================================================
 */
public class AgenteDashboardFragment extends Fragment {

    private AgenteViewModel viewModel;
    private InmuebleAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private EditText etBuscar;
    private ImageButton btnFiltros;
    private TextView tvEmptyState;

    private List<Inmueble> listaCompleta = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agente_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        viewModel = new ViewModelProvider(requireActivity()).get(AgenteViewModel.class);

        setupRecyclerView();
        setupObservers();
        setupSearchAndFilters();

        swipeRefreshLayout.setColorSchemeResources(R.color.blue_primary);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (viewModel != null) {
                viewModel.recargarDatos();
            }
        });
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewInmuebles);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshAgente);
        progressBar = view.findViewById(R.id.progressBar);
        etBuscar = view.findViewById(R.id.etBuscarInmueble);
        btnFiltros = view.findViewById(R.id.btnFiltros);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
    }

    private void setupRecyclerView() {
        adapter = new InmuebleAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnInmuebleClickListener(inmueble -> {
            if (!isAdded()) return;

            try {
                if (inmueble != null && inmueble.getUid() != null) {
                    Bundle args = new Bundle();
                    args.putString("inmuebleId", inmueble.getUid());

                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.action_agente_to_detalle, args);
                }
            } catch (Exception e) {
                Log.e("AgenteDashboard", "Error navegando al detalle", e);
                Toast.makeText(getContext(), "No se pudo abrir el detalle", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupObservers() {
        progressBar.setVisibility(View.VISIBLE);

        viewModel.getCatalogoFiltrado().observe(getViewLifecycleOwner(), inmuebles -> {
            progressBar.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if (inmuebles != null) {
                listaCompleta = inmuebles;
                filtrarPorTexto(etBuscar.getText().toString());
            }
        });
    }

    private void setupSearchAndFilters() {
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarPorTexto(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnFiltros.setOnClickListener(this::mostrarMenuFiltros);
    }

    private void filtrarPorTexto(String query) {
        if (listaCompleta == null) return;

        List<Inmueble> filtrados = new ArrayList<>();
        String q = query.toLowerCase().trim();

        for (Inmueble item : listaCompleta) {
            String dir = item.getDireccion() != null ? item.getDireccion().toLowerCase() : "";
            String desc = item.getDescripcion() != null ? item.getDescripcion().toLowerCase() : "";
            if (dir.contains(q) || desc.contains(q)) {
                filtrados.add(item);
            }
        }

        adapter.setInmuebles(filtrados);

        if (filtrados.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
        }
    }

    private void mostrarMenuFiltros(View anchorView) {
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        popup.getMenuInflater().inflate(R.menu.menu_filtros, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.filtro_venta) {
                viewModel.aplicarFiltro("Venta");
                Toast.makeText(getContext(), "Mostrando: Solo Venta", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.filtro_renta) {
                viewModel.aplicarFiltro("Renta");
                Toast.makeText(getContext(), "Mostrando: Solo Renta", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.filtro_todos) {
                viewModel.aplicarFiltro(null);
                Toast.makeText(getContext(), "Mostrando: Todos los inmuebles", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popup.show();
    }
}
