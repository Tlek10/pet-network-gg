package com.pet.social.event.service;

import com.pet.social.event.dto.EventDTO;
import com.pet.social.event.model.Event;
import com.pet.social.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event createEvent(EventDTO dto, String username) {
        Event event = Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .dateTime(dto.getDateTime())
                .creatorUsername(username)
                .participants(new HashSet<>(List.of(username)))
                .build();
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event joinEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        event.getParticipants().add(username);
        return eventRepository.save(event);
    }

    public Event leaveEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        event.getParticipants().remove(username);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        if (!event.getCreatorUsername().equals(username)) {
            throw new SecurityException("Only the creator can delete this event.");
        }
        eventRepository.delete(event);
    }
}