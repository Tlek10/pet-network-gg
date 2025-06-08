package com.pet.social.event.controller;

import com.pet.social.event.dto.EventDTO;
import com.pet.social.event.model.Event;
import com.pet.social.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(eventService.createEvent(dto, user.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PostMapping("/{eventId}/join")
    public ResponseEntity<Event> joinEvent(@PathVariable("eventId") Long eventId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(eventService.joinEvent(eventId, user.getUsername()));
    }

    @DeleteMapping("/{eventId}/leave")
    public ResponseEntity<Event> leaveEvent(@PathVariable("eventId") Long eventId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(eventService.leaveEvent(eventId, user.getUsername()));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId, @AuthenticationPrincipal User user) {
        eventService.deleteEvent(eventId, user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
