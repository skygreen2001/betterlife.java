package com.zyp.bb;

import com.zyp.bb.respository.BbUserRepository;
import com.zyp.bb.message.amqp.Receiver;
import com.zyp.bb.service.MsgHandleService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by skygreen on 06/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisMsgSendTest {
    @Autowired
    Receiver receiver;

    @Autowired
    private BbUserRepository bbUser;

    @Autowired
    private MsgHandleService msgHandleService;

    @Before
    public void setup() {
    }

    @Test
    public void runReceive() {
        //测试数据
        String message = "{\n" +
                "    \"data\": {\n" +
                "        \"action\": \"finish\",\n" +
                "        \"answeruser\": \"summer\",\n" +
                "        \"basetaskid\": 117,\n" +
                "        \"locationtaskid\": 37,\n" +
                "        \"score\": 0,\n" +
                "        \"teamid\": 3877,\n" +
                "        \"tripid\": 19\n" +
                "    },\n" +
                "    \"receiver\": 5483,\n" +
                "    \"type\": \"TASK\"\n" +
                "}";
        bbUser.set(1320L,"abcde123456~", 1);
        receiver.processMessage(message);
    }

    @Test
    public void runRedis() {
        String message = "{\n" +
                "    \"data\": {\n" +
                "        \"action\": \"finish\",\n" +
                "        \"answeruser\": \"summer\",\n" +
                "        \"basetaskid\": 117,\n" +
                "        \"locationtaskid\": 37,\n" +
                "        \"score\": 0,\n" +
                "        \"teamid\": 3877,\n" +
                "        \"tripid\": 19\n" +
                "    },\n" +
                "    \"receiver\": 5483,\n" +
                "    \"type\": \"TASK\"\n" +
                "}";
        bbUser.set(5483L,"abcde123456~", 1);
        try {
            JSONObject object = new JSONObject(message);
            Long userId = object.optLong("receiver");
            msgHandleService.bakUserMsg(userId, object, false);
            msgHandleService.handleOfflineMsgs("abcde123456~");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
