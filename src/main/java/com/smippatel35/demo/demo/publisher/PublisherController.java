package com.smippatel35.demo.demo.publisher;

import com.smippatel35.demo.demo.exception.LibraryResourceAlreadyExistException;
import com.smippatel35.demo.demo.exception.LibraryResourceNotFoundException;
import com.smippatel35.demo.demo.utils.LibraryUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {

    private PublisherService publisherService;

    //  Constructor based dependency injection.
    public PublisherController(PublisherService publisherService){
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId){

        if (!LibraryUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        Publisher publisher = null;

        try{
            publisher = publisherService.getPublisher(publisherId, traceId);

        } catch (LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId){

        if (!LibraryUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        try {
            publisherService.addPublisher(publisher, traceId);
        } catch (LibraryResourceAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId,
                                             @RequestBody Publisher publisher,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId){

        if (!LibraryUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        try{
            publisher.setPublisherId(publisherId);
            publisherService.updatePublisher(publisher,traceId);
        } catch (LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId){

        if (!LibraryUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }
        
        try{
            publisherService.deletePublisher(publisherId, traceId);
        } catch (LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String name,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId){

        if (!LibraryUtils.doesStringValueExist(name)){
            return new ResponseEntity<>("Please Enter name to search publisher", HttpStatus.BAD_REQUEST);
        } else {

        }

        return new ResponseEntity<>(publisherService.searchPublisher(name, traceId), HttpStatus.OK);

    }
}
