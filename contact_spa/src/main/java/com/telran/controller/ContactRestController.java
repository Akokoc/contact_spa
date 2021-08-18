package com.telran.controller;

import com.telran.mapper.ContactMapper;
import com.telran.service.ContactService;
import org.springframework.web.bind.annotation.*;
import com.telran.dto.ContactToAddDto;
import com.telran.dto.ContactToDisplayDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ContactRestController {

    private final ContactService service;
    private final ContactMapper contactMapper;

    public ContactRestController(ContactService service, ContactMapper contactMapper) {
        this.service = service;
        this.contactMapper = contactMapper;
    }

    //url add:              POST        host/api/contact
    //url edit:             PUT         host/api/contact/{contactId}
    //url getAll:           GET         host/api/contacts
    //url getById:          GET         host/api/contact/{contactId}
    //url deleteById:       DELETE      host/api/contact/{contactId}
    //url searchByName:     GET         host/api/contact/search?name=searchValue

    @PostMapping("/contact")
    public void add(@RequestBody ContactToAddDto contactToAddDto) {
        service.addContact(contactToAddDto.firstName, contactToAddDto.lastName, contactToAddDto.age);
    }

    @PutMapping("/contact/{id}")
    public void edit(@RequestBody ContactToAddDto contactToAddDto, @PathVariable(name = "id") int contactId) {
        service.editContact(contactToAddDto.firstName, contactToAddDto.lastName, contactToAddDto.age, contactId);
    }

    @GetMapping("/contacts")
    public List<ContactToDisplayDto> getAll() {
        return service.getAllContacts()
                .stream()
//                .map(contact -> contactMapper.toDto(contact))
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/contact/{id}")
    public ContactToDisplayDto getById(@PathVariable int id) {
        return contactMapper.toDto(service.getById(id));
    }

    @DeleteMapping("/contact/{id}")
    public void deleteById(@PathVariable int id) {
        service.deleteById(id);
    }

    @GetMapping("contact/search")
    public List<ContactToDisplayDto> searchByName(@RequestParam(name = "name") String searchTerm) {
        return service.searchByName(searchTerm)
                .stream()
                .map(contactMapper::toDto)
                .collect(Collectors.toList());
    }
}
