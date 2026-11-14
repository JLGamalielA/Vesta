package com.example.inmueblecheck.ui.inmuebles;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.inmueblecheck.data.model.Inmueble;
import com.example.inmueblecheck.data.model.Media;
import com.example.inmueblecheck.data.repository.InmuebleRepository;

import java.util.List;

/**
 * ============================================================================
 * Company:       Vesta
 * File:          DetalleInmuebleViewModel.java
 * Layer:         UI / ViewModel (Inmuebles)
 * ============================================================================
 *
 * DESCRIPCIÓN:
 * ViewModel responsable de orquestar los detalles completos de una propiedad.
 * Emplea transformaciones reactivas (Transformations.switchMap) para reaccionar
 * al identificador del inmueble y recuperar información y galería multimedia.
 *
 * ARCHIVOS RELACIONADOS:
 * - InmuebleRepository.java       -> Provee consultas reactivas Room.
 * - Inmueble.java                 -> Modelo de propiedad consultado.
 * - Media.java                    -> Lista de imágenes asociadas.
 * - DetalleInmuebleFragment.java  -> Renderiza vistas y mapa.
 *
 * CONTEXTO DE EJECUCIÓN:
 * - MainThread con suscripciones reactivas LiveData; consultas IO en segundo plano.
 * ============================================================================
 */
public class DetalleInmuebleViewModel extends AndroidViewModel {

    private final InmuebleRepository repository;
    private final MutableLiveData<String> inmuebleIdInput = new MutableLiveData<>();

    private final LiveData<Inmueble> inmueble;
    private final LiveData<List<Media>> mediaList;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
        repository = new InmuebleRepository(application);

        inmueble = Transformations.switchMap(inmuebleIdInput, id ->
                repository.getInmuebleById(id));

        mediaList = Transformations.switchMap(inmuebleIdInput, id ->
                repository.getMediaForInmueble(id));
    }

    public void setInmuebleId(String id) {
        if (id != null) {
            inmuebleIdInput.setValue(id);
        }
    }

    public LiveData<Inmueble> getInmueble() { return inmueble; }
    public LiveData<List<Media>> getMediaList() { return mediaList; }
}