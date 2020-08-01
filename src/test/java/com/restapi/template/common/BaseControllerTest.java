package com.restapi.template.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.template.testfactory.CommentFactory;
import com.restapi.template.testfactory.PostFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "templet.restapi.com", uriPort = 443)
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Disabled
public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PostFactory postFactory;

    @Autowired
    protected CommentFactory commentFactory;

}
