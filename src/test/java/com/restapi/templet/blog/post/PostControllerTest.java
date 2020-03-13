package com.restapi.templet.blog.post;

import com.restapi.templet.blog.post.dto.PostDto;
import com.restapi.templet.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("게시글 테스트")
class PostControllerTest extends BaseControllerTest {


    @Test
    @Transactional
    @DisplayName("포스트 목록 조회")
    void getPosts() throws Exception {
        this.getneratePost(1);
        this.getneratePost(2);
        this.getneratePost(3);
        this.getneratePost(4);
        this.getneratePost(5);
        this.getneratePost(6);
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts")
                .param("page", "1")
                .param("size", "2")
                .param("sort", "title,DESC"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getPosts",
                        responseFields(
                                fieldWithPath("_embedded.postList[].title").description("게시글 제목"),
                                fieldWithPath("_embedded.postList[].writerId").description("작성자 아이디"),
                                fieldWithPath("_embedded.postList[].views").description("조회수"),
                                fieldWithPath("_embedded.postList[].createdDate").description("작성일"),
                                fieldWithPath("_embedded.postList[].modifiedDate").description("수정일"),
                                fieldWithPath("_embedded.postList[]._links.self.href").description("게시글 데이터 링크"),
                                fieldWithPath("_links.self.href").description("Self 링크"),
                                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs"),
                                fieldWithPath("_links.first.href").description("첫번째 목록"),
                                fieldWithPath("_links.prev.href").description("이전 목록"),
                                fieldWithPath("_links.next.href").description("다음 목록"),
                                fieldWithPath("_links.last.href").description("마지막 목록"),
                                fieldWithPath("page.size").description("한 페이지에 조회되는 게시글수"),
                                fieldWithPath("page.totalElements").description("총 게시글수"),
                                fieldWithPath("page.totalPages").description("총 페이지"),
                                fieldWithPath("page.number").description("현재 페이지")
                        )
                ));
    }

    @Test
    @Transactional
    @DisplayName("포스트 저장")
    void savePost() throws Exception {
        PostDto postDto = PostDto.builder()
                .title("포스트 제목")
                .writerId("웹마스터")
                .body("포스트 입력 테스트입니다.")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/blog/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("sendPost",
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("writerId").description("작성자 아이디"),
                                fieldWithPath("body").description("게시글 내용")
                        )));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("포스트 제목"))
                .andExpect(jsonPath("writerId").value("웹마스터"))
                .andExpect(jsonPath("body").value("포스트 입력 테스트입니다."))
                .andExpect(jsonPath("views").value(1));
    }

    @Test
    @Transactional
    @DisplayName("포스트 조회")
    void getPost() throws Exception {
        Post post = this.getneratePost(1);
        this.addComment(post,1);
        this.addComment(post,2);
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}",post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("게시글1"))
                .andExpect(jsonPath("writerId").value("작성자1"))
                .andExpect(jsonPath("body").value("게시글 본문입니다."))
                .andExpect(jsonPath("views").value(1))
                .andDo(print())
                .andDo(document("getPost",
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("writerId").description("작성자 아이디"),
                                fieldWithPath("body").description("게시글 내용"),
                                fieldWithPath("views").description("조회수"),
                                fieldWithPath("createdDate").description("작성일"),
                                fieldWithPath("modifiedDate").description("수정일"),
                                fieldWithPath("comments[].commenterId").description("댓글 작성자 Id"),
                                fieldWithPath("comments[].message").description("댓글"),
                                fieldWithPath("comments[].createdDate").description("댓글 작성시간"),
                                fieldWithPath("comments[].modifiedDate").description("댓글 수정시간"),
                                fieldWithPath("comments[]._links.self.href").description("댓글 Self 링크"),
                                fieldWithPath("comments[]._links.updateComment.href").description("댓글 수정하기"),
                                fieldWithPath("comments[]._links.deleteComment.href").description("댓글 삭제하기"),
                                fieldWithPath("_links.self.href").description("Self 링크"),
                                fieldWithPath("_links.updatePost.href").description("게시글 수정하기"),
                                fieldWithPath("_links.deletePost.href").description("게시글 삭제하기"),
                                fieldWithPath("_links.sendComment.href").description("게시글에 댓글달기"),
                                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs")
                        ),
                        links(
                                linkWithRel("self").description("Self 링크"),
                                linkWithRel("updatePost").description("게시글 수정하기"),
                                linkWithRel("deletePost").description("게시글 삭제하기"),
                                linkWithRel("sendComment").description("게시글에 댓글달기"),
                                linkWithRel("profile").description("해당 링크의 Api Docs")
                        )
                ));

    }

    @Test
    @Transactional
    @DisplayName("포스트 조회(게시글이 없을때)")
    void getPostNoPost() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}", 1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("포스트 수정")
    void updatePost() throws Exception {
        Post post = this.getneratePost(1);
        PostDto postDto = PostDto.builder()
                .title("수정된 포스트")
                .writerId("웹마스터")
                .body("포스트 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/blog/posts/{postId}",post.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updatePost",
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("writerId").description("작성자 아이디"),
                                fieldWithPath("body").description("게시글 내용")
                        )
                ));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}",post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("수정된 포스트"))
                .andExpect(jsonPath("writerId").value("웹마스터"))
                .andExpect(jsonPath("body").value("포스트 수정 테스트입니다."))
                .andExpect(jsonPath("views").value(1));
    }

    @Test
    @Transactional
    @DisplayName("포스트 삭제")
    void deletePost() throws Exception {
        Post post = this.getneratePost(1);
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/blog/posts/{postId}",post.getPostId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("deletePost"));
    }


}
