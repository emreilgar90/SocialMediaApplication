package com.emreilgar.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Comment {
    private String id;
    private String userId;
    private String postId;
    private String username;
    private int likeCount;
    private int dislikeCount;
    @Builder.Default //->kullanıcıdan alınmadığı vakit default
    private long sharedTime=System.currentTimeMillis();

}
