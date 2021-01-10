package com.smippatel35.demo.demo.publisher;

import com.smippatel35.demo.demo.exception.LibraryResourceAlreadyExistException;
import com.smippatel35.demo.demo.exception.LibraryResourceNotFoundException;
import com.smippatel35.demo.demo.utils.LibraryUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public void addPublisher(Publisher publisherToBeAdded, String traceId) throws LibraryResourceAlreadyExistException {

        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber()
        );

        PublisherEntity addedPublisher = null;
        try{
            addedPublisher = publisherRepository.save(publisherEntity);
        } catch (DataIntegrityViolationException e){
            throw new LibraryResourceAlreadyExistException("TraceId: "+ traceId +", Publisher Already Exists!!");
        }

        publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
    }

    public Publisher getPublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException {

        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);

        Publisher publisher = null;

        if (publisherEntity.isPresent()){

            PublisherEntity pe = publisherEntity.get();
            publisher = createPublisherFromEntity(pe);
        } else {
            throw new LibraryResourceNotFoundException("TraceId: "+ traceId +", Publisher Id: "+ publisherId +" Not Found!!");
        }

        return publisher;
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(), pe.getName(), pe.getEmailId(), pe.getPhoneNumber());
    }


    public void updatePublisher(Publisher publisherToBeUpdated, String traceId) throws LibraryResourceNotFoundException {
        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherToBeUpdated.getPublisherId());

        if (publisherEntity.isPresent()){

            PublisherEntity pe = publisherEntity.get();
            if (LibraryUtils.doesStringValueExist(publisherToBeUpdated.getEmailId())){
                pe.setEmailId(publisherToBeUpdated.getEmailId());
            }
            if (LibraryUtils.doesStringValueExist(publisherToBeUpdated.getPhoneNumber())){
                pe.setPhoneNumber(publisherToBeUpdated.getPhoneNumber());
            }
            publisherRepository.save(pe);
            publisherToBeUpdated = createPublisherFromEntity(pe);
        } else {
            throw new LibraryResourceNotFoundException("TraceId: "+ traceId +", Publisher Id: "+ publisherToBeUpdated.getPublisherId() +" Not Found!!");
        }
    }

    public void deletePublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException {
        try {
            publisherRepository.deleteById(publisherId);
        }catch (EmptyResultDataAccessException e){
            throw new LibraryResourceNotFoundException("TraceId: "+ traceId +", Publisher Id: "+ publisherId +" Not Found!!");
        }

    }

    public List<Publisher> searchPublisher(String s, String name) {

        List<PublisherEntity> publisherEntities = null;
        if (LibraryUtils.doesStringValueExist(name)){
            publisherEntities = publisherRepository.findByNameContaining(name);
        }
        if(publisherEntities != null && publisherEntities.size() > 0){
            return publisherEntities
                    .stream()
                    .map(pe -> createPublisherFromEntity(pe))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}
