package server.chat;

/**
 * Created by TaeHwan
 * 2017. 7. 19. PM 4:12
 */
public class ChatMessage {
    private final String command;
    private final String nickname;
    public String text;

    public ChatMessage(String command, String nickname) {
        this.command = command;
        this.nickname = nickname;
    }

}
