package com.microservice.agendacita.controller;

import com.microservice.agendacita.model.Agendar;
import com.microservice.agendacita.model.EstadoCita;
import com.microservice.agendacita.service.AgendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendar")
@RequiredArgsConstructor
public class AgendarController {

    private final AgendarService agendarService;

    // Crear nueva cita
    @PostMapping
    public ResponseEntity<Agendar> crearCita(@RequestBody Agendar agendar) {
        Agendar nuevaCita = agendarService.agendar(agendar);
        return ResponseEntity.ok(nuevaCita);
    }

    // Obtener cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Agendar> obtenerCita(@PathVariable Long id) {
        Agendar cita = agendarService.obtenerPorId(id);
        return ResponseEntity.ok(cita);
    }

    // Listar citas por paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Agendar>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(agendarService.listarPorPaciente(pacienteId));
    }

    // Cancelar cita por ID
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Agendar> cancelarCita(@PathVariable Long id) {
        Agendar citaCancelada = agendarService.cancelar(id);
        return ResponseEntity.ok(citaCancelada);
    }

    // Buscar citas por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<List<Agendar>> listarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(agendarService.listarPorRangoDeFecha(desde, hasta));
    }

    // Buscar citas por estado (AGENDADA, CANCELADA, COMPLETADA)
    @GetMapping("/estado")
    public ResponseEntity<List<Agendar>> listarPorEstado(@RequestParam EstadoCita estado) {
        return ResponseEntity.ok(agendarService.listarPorEstado(estado));
    }
}