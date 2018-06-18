package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.AdRepository;
import bioskopi.rs.repository.BidRepository;
import bioskopi.rs.repository.RegisteredUserRepository;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.jpa.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Ad service
 */
@Service
public class AdServiceImpl implements AdService {

    public static final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private BidRepository bidRepository;

    private final String IMAGE_PATH = Paths.get("img", "ads").toString()
            + File.separator;


    @Override
    public List<Ad> getAllActive() {
        Optional<List<Ad>> temp = adRepository.findAllActive();
        ArrayList<Ad> filtered = new ArrayList<>();
        if(temp.isPresent()){
            List<Ad> ads = temp.get();
            for (Ad ad :
                    ads) {
                if(ad.getDeadline().isAfter(LocalDateTime.now())){
                    ad.setImage(IMAGE_PATH + ad.getImage());
                    filtered.add(ad);
                }

            }
            return filtered;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public List<Ad> getAllWait() {
        Optional<List<Ad>> temp = adRepository.findAllWait();
        ArrayList<Ad> filtered = new ArrayList<>();
        if(temp.isPresent()){
            List<Ad> ads = temp.get();
            for (Ad ad :
                    ads) {
                if(ad.getDeadline().isAfter(LocalDateTime.now())){
                    ad.setImage(IMAGE_PATH + ad.getImage());
                    filtered.add(ad);
                }

            }
            return filtered;
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public Ad findById(long id) {
        Optional<Ad> temp = adRepository.findById(id);
        if(!temp.isPresent()){
            throw new ValidationException("Ad does not exist");
        }
        Ad ad = temp.get();
        ad.setImage(IMAGE_PATH + ad.getImage());
        return ad;
    }

    @Override
    @Transactional
    public Ad add(Ad ad) {
        try {
            LocalDateTime previous10Min = LocalDateTime.now().minusMinutes(10L);
            if(ad.getDeadline().isBefore(previous10Min)){
                throw new ValidationException("Wrong deadline value. Minimum value is current date and time");
            }
        } catch (NullPointerException e){
            throw new ValidationException("Corrupted data received");
        }

        Optional<RegisteredUser> temp = registeredUserRepository.findById(ad.getOwner().getId());
        if(!temp.isPresent()){
            throw new ValidationException("User does not exist");
        }
        ad.setOwner(temp.get());

        return adRepository.save(ad);


    }





    @Override
    @Transactional
    public void accept(Ad ad) {
        try{

            LocalDateTime previous10Min = LocalDateTime.now().minusMinutes(10L);
            if(ad.getDeadline().isBefore(previous10Min)){
                throw new ValidationException("Wrong deadline value. Minimum value is current date and time");
            }

            Optional<RegisteredUser> temp = registeredUserRepository.findById(ad.getOwner().getId());
            if(!temp.isPresent()){
                throw new ValidationException("User does not exist");
            }
            ad.setOwner(temp.get());
            ad.setState(AdState.ACTIVE);
            String[] tokens = ad.getImage().split("/");
            //temp.setImage(tokens[tokens.length - 1]);
            ad.setImage(tokens[tokens.length - 1]);
            adRepository.save(ad);
        }catch (StaleObjectStateException |OptimisticLockException |  ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Data are stale");
        }
        catch (NullPointerException e){
            throw new ValidationException("Corrupted data received");
        }



    }

    @Override
    @Transactional
    public void reject(Ad ad) {
        try{

            LocalDateTime previous10Min = LocalDateTime.now().minusMinutes(10L);
            if(ad.getDeadline().isBefore(previous10Min)){
                throw new ValidationException("Wrong deadline value. Minimum value is current date and time");
            }

            Optional<RegisteredUser> temp = registeredUserRepository.findById(ad.getOwner().getId());
            if(!temp.isPresent()){
                throw new ValidationException("User does not exist");
            }
            ad.setOwner(temp.get());
            ad.setState(AdState.INACTIVE);
            String[] tokens = ad.getImage().split("/");
            //temp.setImage(tokens[tokens.length - 1]);
            ad.setImage(tokens[tokens.length - 1]);
            adRepository.save(ad);
        }catch (StaleObjectStateException |OptimisticLockException |  ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Data are stale");
        }catch (NullPointerException e){
            throw new ValidationException("Corrupted data received");
        }

    }

    @Override
    @Transactional
    public void delete(Ad ad) {
        ad.setState(AdState.INACTIVE);
        try{
            adRepository.save(ad);
        }catch (StaleObjectStateException |OptimisticLockException |  ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Data are stale");
        }

    }

    @Override
    @Transactional
    public Ad addBid(Bid bid) {
        try{
            bid.setState(BidState.WAIT);
            Optional<Ad> ad = adRepository.findById(bid.getAd().getId());
            if(!ad.isPresent()){
                throw new ValidationException("Ad does not exist");
            }
            Optional<RegisteredUser> user = registeredUserRepository.findById(bid.getUser().getId());
            if (!user.isPresent()){
                throw new ValidationException("User does not exist");
            }
            bid.setUser(user.get());
            Ad temp = ad.get();
            if(temp.getState() == AdState.INACTIVE){
                throw  new ValidationException("Offer is not active");
            }
            if(bid.getDate().isAfter(temp.getDeadline())){
                throw new ValidationException("Offer date is after deadline");
            }
            if(bid.getUser().getId() == temp.getOwner().getId()){
                throw new ValidationException("You can not add offer to your ad");
            }
            if(bid.getAd().getVersion() < temp.getVersion()){
                throw new ValidationException("Data are stale");
            }
            boolean exist = false;
            for (Bid b :
                    temp.getBids()) {
                if(b.getUser().getId() == bid.getUser().getId()){
                    b.setOffer(bid.getOffer());
                    exist = true;
                    break;
                }
            }
            if(!exist){
                temp.getBids().add(bid);
            }
            return adRepository.save(temp);
        } catch (NullPointerException e){
            throw new ValidationException("Corrupted data received");
        } catch (OptimisticLockException | StaleObjectStateException | ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Data are stale");
        }
    }

    @Override
    @Transactional
    public Ad acceptOffer(Bid bid,RegisteredUser currentUser) {
        try {
            Optional<Ad> ad = adRepository.findById(bid.getAd().getId());
            if(!ad.isPresent()){
                throw new ValidationException("Ad does not exist");
            }
            Optional<RegisteredUser> user = registeredUserRepository.findById(bid.getUser().getId());
            if (!user.isPresent()){
                throw new ValidationException("User does not exist");
            }
            Ad temp = ad.get();
            if(bid.getDate().isAfter(temp.getDeadline())){
                throw new ValidationException("Offer date is after deadline");
            }
            if(bid.getAd().getVersion() < temp.getVersion()){
                throw new ValidationException("Data are stale");
            }
            boolean found = false;
            for (Bid b :
                    temp.getBids()) {
                if(b.getId().equals(bid.getId())){
                    found = true;
                    break;
                }
            }
            if(!found){
                throw new ValidationException("Corrupted data received");
            }
            if(currentUser.getId() != temp.getOwner().getId()){
                throw new ValidationException("You are not the owner of Ad. You can not accept offer");
            }
            for (Bid b :
                    temp.getBids()) {
                if(b.getId().equals(bid.getId()) ){
                    b.setState(BidState.ACCEPT);
                }else{
                    b.setState(BidState.REJECT);
                }
            }
            temp.setState(AdState.INACTIVE);
            return adRepository.save(temp);
        }catch (NullPointerException e){
            throw new ValidationException("Corrupted data received");
        }catch ( StaleObjectStateException |OptimisticLockException |  ObjectOptimisticLockingFailureException e){
            throw new ValidationException("Data are stale");
        }
    }
}
