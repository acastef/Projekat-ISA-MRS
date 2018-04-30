package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.PropsDTO;
import bioskopi.rs.domain.Props;
import bioskopi.rs.repository.PropsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PropsService
 */
@Service("propsService")
public class PropsServiceImpl implements PropsService {

    @Autowired
    private PropsRepository propsRepository;

    private  final String IMAGE_PATH = Paths.get("img","props").toString()
            + File.separator;

    @Override
    public PropsDTO findByDescription(String description) {

        Props temp = propsRepository.findByDescription(description);
        temp.setImage(IMAGE_PATH +temp.getImage());
        return new PropsDTO(temp);
    }

    @Override
    public List<PropsDTO> findAllProps() {

        List<PropsDTO> temp = new ArrayList<>();
        for (Props prop:  propsRepository.findAll()) {
            prop.setImage(IMAGE_PATH + prop.getImage());
            temp.add(new PropsDTO(prop));
        }
        return temp;
    }
}
