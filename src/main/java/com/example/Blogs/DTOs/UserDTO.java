package com.example.Blogs.DTOs;

import com.example.Blogs.Serializers.OffsetDateTimeDeserializer;
import com.example.Blogs.Serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
public class UserDTO {
    @Setter
    private Long id;
    @Setter
    private String username;
    @Setter
    private String displayName;
    private String email;
    private String password;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime signedUpAt;
    @Setter
    private Map<Long, PostDTO> posts;

    public UserDTO(Long id, String username, String displayName, OffsetDateTime signedUpAt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.signedUpAt = signedUpAt;
    }

    public UserDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String username, String displayName, String email, String password) { //Constructor to create a new user
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }


    public void applyTimeOffset(Long offset) {
        this.signedUpAt = this.signedUpAt.plusMinutes(offset);
    }
}
