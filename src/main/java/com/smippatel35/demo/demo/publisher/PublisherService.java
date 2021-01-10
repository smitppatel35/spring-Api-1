package com.smippatel35.demo.demo.publisher;

import com.smippatel35.demo.demo.exception.LibraryResourceAlreadyExistException;
import com.smippatel35.demo.demo.exception.LibraryResourceNotFoundException;
import com.smippatel35.demo.demo.utils.LibraryUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public void addPublisher(Publisher publisherToBeAdded) throws LibraryResourceAlreadyExistException {

        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber()
        );

        PublisherEntity addedPublisher = null;
        try{
            addedPublisher = publisherRepository.save(publisherEntity);
        } catch (DataIntegrityViolationException e){
            throw new LibraryResourceAlreadyExistException("Publisher Already Exists!!");
        }

        publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
    }

    public Publisher getPublisher(Integer publisherId) throws LibraryResourceNotFoundException {

        Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);

        Publisher publisher = null;

        if (publisherEntity.isPresent()){

            PublisherEntity pe = publisherEntity.get();
            publisher = createPublisherFromEntity(pe);
        } else {
            throw new LibraryResourceNotFoundException("Publisher Id: "+ publisherId +" Not Found!!");
        }

        return publisher;
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(), pe.getName(), pe.getEmailId(), pe.getPhoneNumber());
    }


    public void updatePublisher(Publisher publisherToBeUpdated) throws LibraryResourceNotFoundException {
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
            throw new LibraryResourceNotFoundException("Publisher Id: "+ publisherToBeUpdated.getPublisherId() +" Not Found!!");
        }
    }
}
