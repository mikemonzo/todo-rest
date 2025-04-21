package com.example.todo_rest.task.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todo_rest.task.dto.EditTaskCommand;
import com.example.todo_rest.task.dto.GetTaskDto;
import com.example.todo_rest.task.service.TaskService;
import com.example.todo_rest.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Obtener todas las tareas del usuario",
            description = "Permite obtener todas las tareas de un usuario")
    @ApiResponse(description = "Listado de tareas del usuario", responseCode = "200",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GetTaskDto.class)),
                    examples = {
                            @ExampleObject("""
                                        [
                                            {
                                                "id": 1,
                                                "title": "Comprar alimentos",
                                                "description": "Hacer una lista de compras para el supermercado.",
                                                "createdAt": "2025-01-13T16:12:11.295172",
                                                "deadline": "2025-01-20T16:12:11.295172",
                                                "author": {
                                                    "id": 1,
                                                    "username": "pepe",
                                                    "email": "pepe@openwebinars.net"
                                                }
                                            },
                                            {
                                                "id": 51,
                                                "title": "Pagar facturas",
                                                "description": "Pagar la factura de electricidad antes de la fecha límite.",
                                                "createdAt": "2025-01-13T16:12:11.296628",
                                                "deadline": "2025-01-15T16:12:11.296628",
                                                "author": {
                                                    "id": 1,
                                                    "username": "pepe",
                                                    "email": "pepe@openwebinars.net"
                                                }
                                            }
                                        ]
                                    """)}))
    @GetMapping
    public List<GetTaskDto> getAll(@AuthenticationPrincipal User author) {
        return taskService.findByAuthor(author).stream().map(GetTaskDto::of).toList();
    }

    @Operation(summary = "Obtener una tarea concreta",
            description = "Permite obtener la una tarea concreta si se le proporciona un id")
    @ApiResponse(description = "Información detallada de una tarea", responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetTaskDto.class),
                    examples = {
                            @ExampleObject("""
                                        {
                                                "id": 1,
                                                "title": "Comprar alimentos",
                                                "description": "Hacer una lista de compras para el supermercado.",
                                                "createdAt": "2025-01-13T16:12:11.295172",
                                                "deadline": "2025-01-20T16:12:11.295172",
                                                "author": {
                                                    "id": 1,
                                                    "username": "pepe",
                                                    "email": "pepe@openwebinars.net"
                                                }
                                            }
                                    """)}))
    @PostAuthorize("""
            returnObject.author.username == authentication.principal.username
            """)
    @GetMapping("/{id}")
    public GetTaskDto getById(@RequestParam Long id) {
        return GetTaskDto.of(taskService.findById(id));
    }

    @Operation(summary = "Crear una tarea",
            description = "Permite crear una tarea asociada al usuario autenticado")
    @ApiResponse(description = "Tarea recién creada", responseCode = "201", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = GetTaskDto.class),
            examples = {@ExampleObject("""
                        {
                                "id": 1,
                                "title": "Comprar alimentos",
                                "description": "Hacer una lista de compras para el supermercado.",
                                "createdAt": "2025-01-13T16:12:11.295172",
                                "deadline": "2025-01-20T16:12:11.295172",
                                "author": {
                                    "id": 1,
                                    "username": "pepe",
                                    "email": "pepe@openwebinars.net"
                                }
                            }
                    """)}))
    @PostMapping
    public ResponseEntity<GetTaskDto> create(@RequestBody EditTaskCommand newTaskCommand,
            @AuthenticationPrincipal User author) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetTaskDto.of(taskService.save(newTaskCommand, author)));
    }

    @Operation(summary = "Editar una tarea",
            description = "Permite editar una tarea asociada al usuario autenticado si se proporciona su ID")
    @ApiResponse(description = "Tarea editada", responseCode = "200", content = @Content(
            mediaType = "application/json", schema = @Schema(implementation = GetTaskDto.class),
            examples = {@ExampleObject("""
                        {
                                "id": 1,
                                "title": "Comprar alimentos",
                                "description": "Hacer una lista de compras para el supermercado.",
                                "createdAt": "2025-01-13T16:12:11.295172",
                                "deadline": "2025-01-20T16:12:11.295172",
                                "author": {
                                    "id": 1,
                                    "username": "pepe",
                                    "email": "pepe@openwebinars.net"
                                }
                            }
                    """)}))
    @PreAuthorize("""
            @ownerCheck.check(#id, authentication.principal.getId())
            """)
    @PutMapping("/{id}")
    public ResponseEntity<GetTaskDto> update(@RequestBody EditTaskCommand editTaskCommand,
            @PathVariable Long id) {
        return ResponseEntity.ok(GetTaskDto.of(taskService.edit(editTaskCommand, id)));
    }

    @Operation(summary = "Eliminar una tarea",
            description = "Permite eliminar una tarea asociada al usuario autenticado si se proporciona su ID")
    @ApiResponse(description = "Respuesta correcta de tarea eliminada", responseCode = "204",
            content = @Content(schema = @Schema(implementation = Void.class)))
    @PreAuthorize("""
            @ownerCheck.check(#id, authentication.principal.getId())
            """)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
