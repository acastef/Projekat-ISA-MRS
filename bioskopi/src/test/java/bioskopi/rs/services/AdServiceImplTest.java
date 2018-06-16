package bioskopi.rs.services;

import bioskopi.rs.domain.*;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.AdRepository;
import bioskopi.rs.repository.BidRepository;
import bioskopi.rs.repository.RegisteredUserRepository;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bioskopi.rs.constants.AdConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdServiceImplTest {


    @Autowired
    private AdService adService;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private BidRepository bidRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

        RegisteredUser user1 = new RegisteredUser("user3", "user", "user", "user",
                "user", "user",true,"user","user", new HashSet<>(),
                new HashSet<>(),new ArrayList<>());

        RegisteredUser user2 = new RegisteredUser("user4", "user4", "user4", "user4",
                "user4", "user4",true,"user4","user4", new HashSet<>(),
                new HashSet<>(),new ArrayList<>());


        RUSER1 = registeredUserRepository.save(user1);
        RUSER2 = registeredUserRepository.save(user2);


        Ad ad1 = new Ad(DB_IMG1,DB_NM1,DB_DS1,DB_DL1,DB_ST1,RUSER1,new HashSet<>(),DB_VR1);
        Ad ad2 = new Ad(DB_IMG2,DB_NM2,DB_DS2,DB_DL2,DB_ST2,RUSER1,new HashSet<>(),DB_VR2);

        AD = adRepository.saveAll(new ArrayList<Ad>(){{
            add(ad1);
            add(ad2);
        }}).get(0);

    }

    @Test
    public void getAllActive() {
        List<Ad> allActive = adService.getAllActive();
        assertThat(allActive).hasSize(DB_COUNT1);
    }

    @Test
    public void getAllWait() {
        List<Ad> allWait = adService.getAllWait();
        assertThat(allWait).hasSize(DB_COUNT2);
    }

    @Test
    public void findById() {
        Ad ad = adService.findById(AD.getId());
        assertThat(ad).isNotNull();
        assertThat(ad.getId()).isEqualTo(AD.getId());
        assertThat(ad.getDescription()).isEqualTo(AD.getDescription());
        assertThat(ad.getName()).isEqualTo(AD.getName());
        assertThat(ad.getState()).isEqualTo(AD.getState());
        assertThat(ad.getVersion()).isEqualTo(AD.getVersion());
    }

    @Test
    @Transactional
    public void add() {
        Ad ad3 = new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,RUSER1,new HashSet<>(),NEW_VR);
        int count = adRepository.findAll().size();
        Ad added = adService.add(ad3);
        assertThat(added).isNotNull();
        assertThat(adRepository.findAll()).hasSize(count +1);
    }


    /**
     * Deadline is before current date
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addNotValidDeadline(){
        Ad ad3 = new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,RUSER1,new HashSet<>(),NEW_VR);
        ad3.setDeadline(LocalDateTime.now().minusHours(1));
        adService.add(ad3);
    }

    /**
     * Not valid data
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addNotValidData(){
        Ad ad3 = new Ad();
        adService.add(ad3);
    }

    /**
     * User does not exist in database
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addNotValidUser(){
        Ad ad3 = new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,new RegisteredUser(),new HashSet<>(),NEW_VR);
        adService.add(ad3);
    }

    @Test
    @Transactional
    public void accept() {
        Ad ad = adRepository.findById(AD.getId()).get();
        long version = ad.getVersion();
        adService.accept(ad);
        ad = adRepository.findById(AD.getId()).get();
        assertThat(ad.getState()).isEqualTo(AdState.ACTIVE);
    }

    @Test
    public void reject() {
        Ad ad = adRepository.findById(AD.getId()).get();
        long version = ad.getVersion();
        adService.reject(ad);
        ad = adRepository.findById(AD.getId()).get();
        assertThat(ad.getVersion()).isEqualTo(version+1);
        assertThat(ad.getState()).isEqualTo(AdState.INACTIVE);
    }


    @Test
    public void delete() {
        Ad ad = adService.findById(AD.getId());
        adService.delete(ad);
        ad = adService.findById(AD.getId());
        assertThat(ad.getState()).isEqualTo(AdState.INACTIVE);
    }

    @Test
    @Transactional
    public void addBid(){
        Bid bid = new Bid(25.36,LocalDateTime.now().minusMinutes(12),RUSER2,AD,BidState.WAIT);
        int count = AD.getBids().size();
        Ad ad = adService.addBid(bid);
        assertThat(ad.getBids()).hasSize(count + 1);
    }

    /**
     * Bid date is after deadline
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addBidNotValidDate() {
        Bid bid = new Bid(25.36,AD.getDeadline().plusHours(2),RUSER2,AD,BidState.WAIT);
        adService.addBid(bid);
    }

    /**
     * Owner of bid cannot add offer
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addBidNotValidUser() {
        Bid bid = new Bid(25.36,AD.getDeadline().minusMinutes(12),RUSER1,AD,BidState.WAIT);
        adService.addBid(bid);
    }

    /**
     * User that makes a bid does not exist in database
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addBidNotValidBidUser() {
        Bid bid = new Bid(25.36,AD.getDeadline().minusMinutes(12),new RegisteredUser(),AD,BidState.WAIT);
        adService.addBid(bid);
    }

    /**
     * Ad does not exist in database
     */
    @Test(expected = ValidationException.class)
    @Transactional
    public void addBidNotValidAd() {
        Bid bid = new Bid(25.36,AD.getDeadline().minusMinutes(12),RUSER2,
                new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,new RegisteredUser(),new HashSet<>(),NEW_VR),BidState.WAIT);
        adService.addBid(bid);
    }

    @Test
    @Transactional
    public void acceptOffer() {
        Bid bid1 = new Bid(25.36,LocalDateTime.now().minusMinutes(7),RUSER2,AD,BidState.WAIT);
        Bid bid2 = new Bid(47.21,LocalDateTime.now().minusMinutes(12),RUSER2,AD,BidState.WAIT);
        adService.addBid(bid1);
        Set<Bid> bids = adService.addBid(bid2).getBids();
        Bid temp = bids.iterator().next();
        Ad ad = adService.acceptOffer(temp,RUSER1);
        for (Bid b :
                ad.getBids()) {
            if(b.getId().equals(temp.getId())){
                assertThat(b.getState()).isEqualTo(BidState.ACCEPT);
            }else{
                assertThat(b.getState()).isEqualTo(BidState.REJECT);
            }
        }
        assertThat(ad.getState()).isEqualTo(AdState.INACTIVE);
    }

    @Test(expected = ValidationException.class)
    @Transactional
    public void acceptOfferWrongBid() {
        Bid bid1 = new Bid(25.36,LocalDateTime.now().minusMinutes(7),RUSER2,AD,BidState.WAIT);
        adService.addBid(bid1);
        Ad ad3 = new Ad(NEW_IMG,NEW_NM,NEW_DS,NEW_DL,NEW_ST,RUSER1,new HashSet<>(),NEW_VR);
        Bid bid2 = new Bid(47.21,LocalDateTime.now().minusMinutes(12),RUSER2,ad3,BidState.WAIT);
        ad3.getBids().add(bid2);
        ad3 = adService.add(ad3);
        Bid temp = ad3.getBids().iterator().next();
        temp.setAd(AD);
        adService.acceptOffer(temp,RUSER1);
    }
}