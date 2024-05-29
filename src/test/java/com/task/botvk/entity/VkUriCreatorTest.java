package com.task.botvk.entity;

import com.task.botvk.ConfigByUri;
import com.task.botvk.dto.SendMessagesDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConfigByUri.class, loader = AnnotationConfigContextLoader.class)
public class VkUriCreatorTest {
    @Autowired
    private VkUriCreator vkUriCreator;

    @Test
    public void createUriWithParams() {
        String str = "https://api.vk.com/method/messages.send?access_token=test&v=5.236&group_id=0&message=qwe&user_id=1";
        SendMessagesDto dto = SendMessagesDto.builder().userId(1L).message("qwe").groupId(0L).build();
        URI uri = vkUriCreator.createUri(dto);

        assertEquals(str, uri.toString());
    }

    @Test
    public void createUriWithoutParams() {
        String str = "https://api.vk.com/method/messages.send?access_token=test&v=5.236";
        SendMessagesDto dto = SendMessagesDto.builder().build();
        URI uri = vkUriCreator.createUri(dto);

        assertEquals(str, uri.toString());
    }
}