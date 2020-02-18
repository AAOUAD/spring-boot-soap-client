package taa.poc.springbootsoapclient;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadLocalAuditData {

    @Data
    @NoArgsConstructor
    public static class Messages {

        List<MessageEntity> requestMessages = new ArrayList<>();
        List<MessageEntity> responseMessages = new ArrayList<>();

    }

    private ThreadLocalAuditData() {
    }

    private static ThreadLocal<Messages> threadLocal = ThreadLocal.withInitial(Messages::new);

    public static void setResponseMessage(MessageEntity message) {
        threadLocal.get().responseMessages.add(message);
    }

    public static void setRequestMessage(MessageEntity message) {
        threadLocal.get().requestMessages.add(message);
    }

    public static Messages getMessages() {
        Messages messages = threadLocal.get();
        threadLocal.remove();
        return messages;
    }

    public static void removeMessages() {
        try {
            getMessages();
        } catch (Exception logged) {
            log.warn("CANNOT RELEASE SOAP MESSAGES : {}", logged.getMessage());
        }
    }

}

