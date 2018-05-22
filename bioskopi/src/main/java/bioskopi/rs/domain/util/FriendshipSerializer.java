package bioskopi.rs.domain.util;

import bioskopi.rs.domain.DTO.UserDTO;
import bioskopi.rs.domain.Friendship;
import bioskopi.rs.domain.RegisteredUser;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendshipSerializer extends StdSerializer<Map<RegisteredUser, Friendship>> {
    public FriendshipSerializer(){
        this(null);
    }

    public FriendshipSerializer(Class<Map<RegisteredUser, Friendship>> c ){
        super(c);
    }

    @Override
    public void serialize(Map<RegisteredUser, Friendship> friendships,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        List<UserDTO> friends = new ArrayList<UserDTO>();
        for(Friendship f : friendships.values()){
            friends.add(new UserDTO(f.getSecond().getId(), f.getSecond().getName(),
                    f.getSecond().getSurname(), f.getSecond().getEmail(), f.getSecond().getUsername(),
                    f.getSecond().getAvatar(), f.getSecond().getTelephone(), f.getSecond().getAddress()));
        }
        jsonGenerator.writeObject(friends);
    }
}
