package com.restapi.template.testfactory;

import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostFactory extends AccountFactory {

    @Autowired
    protected PostRepository postRepository;

    @Transactional
    public Post generatePost(int i) {
        return this.postRepository.save(
                new Post(
                        null,
                        generateUserAndGetUser(i),
                        "게시글" + i,
                        "게시글 본문입니다."
                ));
    }
}
