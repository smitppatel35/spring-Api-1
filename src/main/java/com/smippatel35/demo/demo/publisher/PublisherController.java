package com.smippatel35.demo.demo.publisher;

import com.smippatel35.demo.demo.exception.LibraryResourceAlreadyExistException;
import com.smippatel35.demo.demo.exception.LibraryResourceNotFoundException;
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
    public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId){
        Publisher publisher = null;

        try{
            publisher = publisherService.getPublisher(publisherId);

        } catch (LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher){
        try {
            publisherService.addPublisher(publisher);
        } catch (LibraryResourceAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }


}
