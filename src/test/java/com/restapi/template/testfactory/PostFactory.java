package com.restapi.template.testfactory;

import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostFactory {

    @Autowired
    protected PostRepository postRepository;

    @Transactional
    public Post generatePost(int i) {
        Post post = Post.builder()
                .title("게시글" + i)
                .writerId("TestUser" + i)
                .body("게시글 본문입니다.")
                .build();
        return this.postRepository.save(post);
    }
}
