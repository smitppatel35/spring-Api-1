package com.smippatel35.demo.demo.publisher;

import com.smippatel35.demo.demo.exception.LibraryResourceAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {

    private PublisherService publisherService;

    //  Constructor based dependency injection.
    public PublisherController(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public Publisher getPublisher(@PathVariable Integer publisherId){
        return new Publisher(publisherId, "TEst name", "test email@email.com", "123456789");
    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher){
        try {
            publisher = publisherService.addPublisher(publisher);
        } catch (LibraryResourceAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }
}
