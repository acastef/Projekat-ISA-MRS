package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Props;
import bioskopi.rs.domain.PropsReservation;
import bioskopi.rs.domain.util.ValidationException;
import bioskopi.rs.repository.FacilityRepository;
import bioskopi.rs.repository.PropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of PropsService
 */
@Service("propsService")
public class PropsServiceImpl implements PropsService {

    @Autowired
    private PropsRepository propsRepository;

    @Autowired
    private FacilityRepository facilityRepository;


    private final String IMAGE_PATH = Paths.get("img", "props").toString()
            + File.separator;

    @Override
    public PropsDTO findByDescription(String description) {

        Props temp = propsRepository.findByDescription(description);
        temp.setImage(IMAGE_PATH + temp.getImage());
        return new PropsDTO(temp);
    }

    @Override
    public List<PropsDTO> findAllProps() {

        List<PropsDTO> temp = new ArrayList<>();
        for (Props prop : propsRepository.getAllActive()) {
            prop.setImage(IMAGE_PATH + prop.getImage());
            temp.add(new PropsDTO(prop));
        }
        return temp;
    }


    @Override
    public PropsDTO findById(long id) {
        return new PropsDTO(propsRepository.getOne(id));
    }


    @Override
    @Transactional
    public Props add(Props props) throws ValidationException {
        try{
            Optional<Facility> temp = facilityRepository.findById(props.getFacility().getId());
            if(!temp.isPresent()){
                throw new ValidationException("Facility does not exist");
            }
        }catch (NullPointerException e){
            throw new ValidationException("Facility does not exist");
        }

        return propsRepository.save(props);
    }

    @Override
    @Transactional
    public void delete(Props props) {
        Optional<Props> temp = propsRepository.findById(props.getId());
        if(temp.isPresent()){
            props.setActive(false);
            propsRepository.save(props);
        }else {
            throw new ValidationException("Props does not exist");
        }
    }
}
